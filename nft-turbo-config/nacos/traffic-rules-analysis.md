# Nacos流量配置规则分析结论

## 一、总体结论
当前 `/nacos` 目录中的流量规则采用了 Sentinel + Nacos 的三层限流设计：
1. 网关入口限流（gateway-flow-rules）
2. 业务资源限流（business-flow-rules）
3. 热点参数限流（business-param-flow-rules）

整体架构思路合理，但存在一处高概率配置问题：参数限流 datasource 的配置层级疑似不规范，可能导致热点参数规则未生效。

## 二、规则明细

### 1) 业务流控规则（business-flow-rules）
规则类型：QPS（`grade: 1`），直接限流（`strategy: 0`），快速失败（`controlBehavior: 0`）

- `/trade/normalBuy`：800 QPS
- `/trade/buy`：3500 QPS
- `ORDER_CREATE`：1000 QPS
- `INVENTORY_DECREASE`：3500 QPS

代码侧映射：
- `/trade/buy`、`/trade/normalBuy` 对应 `@SentinelResource`
- `ORDER_CREATE` 对应 `SphU.entry("ORDER_CREATE")`
- `INVENTORY_DECREASE` 对应 `SphO.entry("INVENTORY_DECREASE")`

### 2) 热点参数流控（business-param-flow-rules）
- 资源：`GOODS_SALE`
- 参数下标：`paramIdx: 0`
- 阈值：`count: 800`
- 统计窗口：`durationInSec: 1`

含义：按第 0 个参数值做单独统计，每个参数值每秒最多 800。

代码侧映射：
- `SphO.entry("GOODS_SALE", EntryType.IN, 1, request.getGoodsId() + "_" + request.getGoodsType())`

### 3) 网关流控规则（gateway-flow-rules）
- `nfturbo-auth`：1000 QPS
- `nfturbo-business`：35000 QPS

匹配方式：规则 `resource` 与 Gateway Route ID 对齐。

## 三、关键风险点

### 风险1：参数流控配置层级疑似错误（高风险）
`limiter.yml` 当前将 `param-flow` 放在 `spring.cloud.sentinel.param-flow` 下，
常见可生效写法是放在 `spring.cloud.sentinel.datasource.<name>` 并设置 `rule-type: PARAM_FLOW`。

影响：`business-param-flow-rules` 可能不会被 Sentinel 正确加载，导致 `GOODS_SALE` 热点限流形同虚设。

### 风险2：多个接口共用 `/trade/buy` 配额（中风险）
`buy/newBuy/newBuyPlus` 共用同一 Sentinel 资源 `/trade/buy`，会共享 3500 QPS，存在互相抢占配额的情况。

### 风险3：网关与应用阈值量级不一致（中风险）
网关侧 `nfturbo-business` 为 35000 QPS，但关键业务资源 `ORDER_CREATE` 为 1000 QPS。

影响：高峰期更可能在应用层快速失败，网关侧保护提前削峰效果有限。

## 四、建议
1. 优先修正 `PARAM_FLOW` 的 Nacos datasource 配置为标准写法并联调验证。
2. 评估是否拆分 `/trade/buy` 资源，避免不同交易路径互相挤占。
3. 联动校准网关阈值与核心业务阈值，形成分层削峰（网关先削峰、应用再兜底）。
