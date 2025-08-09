# 2025-07-07 增加微信支付流水表,用于对账核对

CREATE TABLE `wechat_transaction` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
      `date` datetime NOT NULL COMMENT '交易时间',
      `app_id` varchar(64) NOT NULL COMMENT '公众账号ID',
      `mch_id` varchar(64) NOT NULL COMMENT '商户号',
      `sub_mch_id` varchar(64) DEFAULT NULL COMMENT '子商户号/特约商户号',
      `device_info` varchar(128) DEFAULT NULL COMMENT '设备号',
      `wechat_order_no` varchar(128) NOT NULL COMMENT '微信订单号',
      `mch_order_no` varchar(128) NOT NULL COMMENT '商户订单号',
      `user_id` varchar(128) NOT NULL COMMENT '用户标识',
      `type` varchar(64) NOT NULL COMMENT '交易类型',
      `status` varchar(64) NOT NULL COMMENT '交易状态',
      `bank` varchar(128) DEFAULT NULL COMMENT '付款银行',
      `currency` varchar(32) DEFAULT NULL COMMENT '货币种类',
      `amount` decimal(18,6) NOT NULL COMMENT '总金额',
      `envelope_amount` decimal(18,6) DEFAULT NULL COMMENT '企业红包金额/代金券金额',
      `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
      `packet` text COMMENT '商户数据包',
      `poundage` decimal(18,6) DEFAULT NULL COMMENT '手续费',
      `rate` varchar(32) DEFAULT NULL COMMENT '费率',
      `order_amount` decimal(18,6) DEFAULT NULL COMMENT '订单金额',
      `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `deleted` tinyint DEFAULT '0' COMMENT 'l是否逻辑删除，0为未删除，非0为已删除''',
      `lock_version` int NOT NULL COMMENT '乐观锁版本号',
      `refund_apply_time` datetime DEFAULT NULL COMMENT '退款申请时间',
      `refund_success_time` datetime DEFAULT NULL COMMENT '退款成功时间',
      `wx_refund_order_no` varchar(255) DEFAULT NULL COMMENT '微信退款单号',
      `mch_refund_order_no` varchar(255) DEFAULT NULL COMMENT '商户退款单号',
      `refund_amount` decimal(18,6) DEFAULT NULL COMMENT '退款金额',
      `envelope_refund_amount` decimal(18,6) DEFAULT NULL COMMENT '充值券退款金额',
      `refund_type` varchar(64) DEFAULT NULL COMMENT '退款类型',
      `refund_status` varchar(64) DEFAULT NULL COMMENT '退款状态',
      `apply_refund_amount` decimal(18,6) DEFAULT NULL COMMENT '申请退款金额',
      PRIMARY KEY (`id`),
      UNIQUE KEY `uk_wechat_order_no` (`wechat_order_no`),
      UNIQUE KEY `uk_mch_order_no` (`mch_order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='微信支付交易流水表';


# 2025-05-20 藏品相关流水表更新为唯一性索引

ALTER TABLE `collection_airdrop_stream`
    ADD Unique KEY `uk_cid_iden_type`(`collection_id`,`stream_type`,`identifier`) USING BTREE
;


ALTER TABLE `collection_stream`
    ADD Unique KEY `uk_cid_iden_type`(`collection_id`,`stream_type`,`identifier`) USING BTREE
;


ALTER TABLE `collection_inventory_stream`
DROP KEY `idx_cid_ident_type`,
	ADD Unique KEY `uk_cid_ident_type`(`collection_id`,`identifier`,`stream_type`) USING BTREE
;


ALTER TABLE `blind_box_inventory_stream`
DROP KEY `idx_cid_ident_type`,
	ADD Unique KEY `uk_cid_ident_type`(`identifier`,`stream_type`) USING BTREE
;

# 2025-05-10 transaction_log表更新为唯一性索引

ALTER TABLE `transaction_log`
	DROP KEY `idx_businsess_trans_id`,
	ADD Unique KEY `uk_businsess_trans_id`(`transaction_id`,`business_scene`,`business_module`) USING BTREE


# 2025-03-10 增加transaction_log表

CREATE TABLE `transaction_log` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `gmt_create` datetime NOT NULL COMMENT '创建时间',
   `gmt_modified` datetime NOT NULL COMMENT '更新时间',
   `transaction_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事务id',
   `business_scene` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务场景',
   `business_module` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务模块',
   `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
   `lock_version` int NULL COMMENT '版本号',
   `deleted` tinyint NULL COMMENT '逻辑删除字段',
   `cancel_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'cancel的类型',
   PRIMARY KEY (`id`),
   KEY `idx_businsess_trans_id`(`transaction_id`,`business_scene`,`business_module`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb3 COLLATE=utf8_general_ci COMMENT='事务记录表';

# 2025-03-10 增加occupied_inventory字段,废弃saleable_inventory字段
ALTER TABLE `blind_box`
    MODIFY COLUMN `occupied_inventory` bigint NULL COMMENT '已占用库存（已废弃）' AFTER `saleable_inventory`,
    ADD COLUMN `frozen_inventory` bigint NULL DEFAULT 0  COMMENT '被冻结库存' AFTER `occupied_inventory`
;

ALTER TABLE `collection`
    MODIFY COLUMN `occupied_inventory` bigint NULL COMMENT '已占用库存（已废弃）' AFTER `identifier`,
    ADD COLUMN `frozen_inventory` int DEFAULT 0  COMMENT '被冻结库存' AFTER `occupied_inventory`
;

ALTER TABLE `blind_box_inventory_stream`
    ADD COLUMN `frozen_inventory` bigint NULL COMMENT '被冻结库存' AFTER `occupied_inventory`
;

ALTER TABLE `collection_inventory_stream`
    ADD COLUMN `frozen_inventory` bigint NULL COMMENT '被冻结库存' AFTER `occupied_inventory`
;

ALTER TABLE `collection_stream`
    ADD COLUMN `frozen_inventory` bigint NULL COMMENT '被冻结库存' AFTER `occupied_inventory`
;

# 2025-02-04 新增预约记录表

CREATE TABLE `goods_book` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
  `goods_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品类型',
  `buyer_id` varchar(128) DEFAULT NULL COMMENT '买家id',
  `buyer_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '买家类型',
  `identifier` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '幂等号',
  `book_succeed_time` datetime DEFAULT NULL COMMENT '预定成功时间',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 AVG_ROW_LENGTH=2340 ROW_FORMAT=DYNAMIC COMMENT='商品预定表'
;

# 2025-02-04 新增持有藏品流水表

CREATE TABLE `held_collection_stream` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `held_collection_id` bigint NOT NULL COMMENT '持有藏品的id',
  `stream_type` varchar(64) NOT NULL COMMENT '流水类型',
  `operator` varchar(64) NOT NULL COMMENT '操作者',
  `identifier` varchar(128) NOT NULL COMMENT '幂等号',
  `deleted` tinyint NULL COMMENT ' 逻辑删除',
  `lock_version` int NULL COMMENT ' 版本号',
  PRIMARY KEY (`id`),
  KEY `idx_held_id`(`held_collection_id`) USING BTREE,
  Unique KEY `uk_held_id_type_iden`(`held_collection_id`,`stream_type`,`identifier`) USING BTREE
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8
COMMENT='持有藏品流水表';


# 2025-02-04 新增空投表

CREATE TABLE `collection_airdrop_stream` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
  `collection_id` bigint DEFAULT NULL COMMENT '藏品id',
  `recipient_user_id` varchar(128) DEFAULT NULL COMMENT '接收用户ID',
  `quantity` bigint DEFAULT NULL COMMENT '藏品空投数量',
  `stream_type` varchar(128) DEFAULT NULL COMMENT '流水类型',
  `identifier` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '幂等号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111880 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci AVG_ROW_LENGTH=16384 ROW_FORMAT=DYNAMIC COMMENT='藏品空投流水表'
;

# 2025-02-02 藏品&盲盒增加预约配置
-- 如果你开启了 es 查询藏品的话，需要在es 和 canal 上增加相关配置，详见文档: https://thoughts.aliyun.com/workspaces/6655879cf459b7001ba42f1b/docs/67a1a63d3fb9180001ce6e2c

ALTER TABLE `collection`
  ADD COLUMN `book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间' AFTER `sync_chain_time`,
  ADD COLUMN `book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间' AFTER `book_start_time`,
  ADD COLUMN `can_book` int DEFAULT NULL COMMENT '是否可以预约' AFTER `book_end_time`,

ALTER TABLE `blind_box`
  ADD COLUMN `book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间' AFTER `collection_configs`,
  ADD COLUMN `book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间' AFTER `book_start_time`,
  ADD COLUMN `can_book` int DEFAULT NULL COMMENT '是否可以预约' AFTER `book_end_time`,

#  2025-01-21 支付单增加支付失败时间
ALTER TABLE `pay_order`
    ADD COLUMN `pay_failed_time` datetime NULL COMMENT '支付失败时间' AFTER `refund_channel_stream_id`
;

#  2025-01-21 增加held_collection_stream表
CREATE TABLE `held_collection_stream` (
  `id` bigint NOT NULL COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `held_collection_id` bigint NOT NULL COMMENT '持有藏品的id',
  `stream_type` varchar(64) NOT NULL COMMENT '流水类型',
  `operator` varchar(64) NOT NULL COMMENT '操作者',
  `identifier` varchar(128) NOT NULL COMMENT '幂等号',
  `deleted` tinyint NULL COMMENT ' 逻辑删除',
  `lock_version` int NULL COMMENT ' 版本号',
  PRIMARY KEY (`id`),
  KEY `idx_held_id`(`held_collection_id`) USING BTREE,
  Unique KEY `uk_held_id_type_iden`(`held_collection_id`,`stream_type`,`identifier`) USING BTREE
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8
COMMENT='持有藏品流水表';


# 2024-12-31 held_collection 增加参考价格和稀有度

ALTER TABLE `held_collection`
	ADD COLUMN `reference_price` decimal(18,6)  NULL COMMENT ' 参考价格' AFTER `biz_type`,
	ADD COLUMN `rarity` varchar(64) NULL COMMENT ' 稀有度' AFTER `reference_price`


# 2024-12-31 新增盲盒相关表

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box   */
/******************************************/
CREATE TABLE `blind_box` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
  `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
  `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
  `detail` text COMMENT '详情',
  `identifier` varchar(128) DEFAULT NULL COMMENT '幂等号',
  `state` varchar(128) DEFAULT NULL COMMENT '状态',
  `quantity` bigint DEFAULT NULL COMMENT '盲盒数量',
  `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
  `saleable_inventory` bigint DEFAULT NULL COMMENT '可销售库存',
  `occupied_inventory` bigint DEFAULT NULL COMMENT '已占用库存',
  `create_time` datetime DEFAULT NULL COMMENT '盲盒创建时间',
  `sale_time` datetime DEFAULT NULL COMMENT '盲盒发售时间',
  `allocate_rule` varchar(512) DEFAULT NULL COMMENT '盲盒分配规则',
  `sync_chain_time` datetime DEFAULT NULL COMMENT '上链时间',
  `creator_id` varchar(128) DEFAULT NULL COMMENT '创建者',
  `collection_configs` text COMMENT '藏品配置',
  `book_start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
  `book_end_time` datetime DEFAULT NULL COMMENT '预约结束时间',
  `can_book` int DEFAULT NULL COMMENT '是否可以预约',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  KEY `idx_state_name` (`state`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COMMENT='盲盒表'
;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box_inventory_stream   */
/******************************************/
CREATE TABLE `blind_box_inventory_stream` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后更新时间',
  `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
  `changed_quantity` bigint DEFAULT NULL COMMENT '本次变更的数量',
  `price` decimal(18,6) DEFAULT NULL COMMENT '价格',
  `quantity` bigint DEFAULT NULL COMMENT '藏品数量',
  `state` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `saleable_inventory` bigint DEFAULT NULL COMMENT '可售库存',
  `occupied_inventory` bigint DEFAULT NULL COMMENT '已占库存',
  `stream_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '流水类型',
  `identifier` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '幂等号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  `extend_info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '扩展信息',
  PRIMARY KEY (`id`),
  KEY `idx_cid_ident_type` (`identifier`,`stream_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=561 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci AVG_ROW_LENGTH=16384 ROW_FORMAT=DYNAMIC COMMENT='盲盒表库存流水'
;

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = blind_box_item   */
/******************************************/
CREATE TABLE `blind_box_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后更新时间',
  `blind_box_id` bigint DEFAULT NULL COMMENT '盲盒id',
  `name` varchar(512) DEFAULT NULL COMMENT '盲盒名称',
  `cover` varchar(512) DEFAULT NULL COMMENT '盲盒封面',
  `collection_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '藏品名称',
  `collection_cover` varchar(512) DEFAULT NULL COMMENT '藏品封面',
  `collection_detail` text COMMENT '藏品详情',
  `collection_serial_no` varchar(128) DEFAULT NULL COMMENT '持有藏品的序列号',
  `state` varchar(128) DEFAULT NULL COMMENT '状态',
  `user_id` varchar(128) DEFAULT NULL COMMENT '持有人id',
  `purchase_price` decimal(18,6) DEFAULT NULL COMMENT '购入价格',
  `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
  `deleted` int DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  `rarity` varchar(32) DEFAULT NULL COMMENT '稀有度',
  `reference_price` decimal(18,6) DEFAULT NULL COMMENT '市场参考价',
  `opened_time` datetime DEFAULT NULL COMMENT ' 开盒时间',
  `assign_time` datetime DEFAULT NULL COMMENT ' 分配时间',
  PRIMARY KEY (`id`),
  KEY `idx_state_box_id` (`blind_box_id`,`state`),
  KEY `idx_user` (`order_id`),
  KEY `idx_order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4011 DEFAULT CHARSET=utf8mb3 COMMENT='盲盒条目表'
;


# 2024-09-20 pay_order 针对支付单号增加唯一性约束
ALTER TABLE `pay_order`
	DROP KEY `idx_pay_order`,
	ADD Unique KEY `uk_pay_order`(`pay_order_id`) USING BTREE

# 2024-08-31 trade_order 表新增reverse_buyer_id

ALTER TABLE `trade_order_0000`
	ADD COLUMN `reverse_buyer_id` varchar(32) NULL COMMENT '逆序的买家ID' AFTER `buyer_id`,
	ADD KEY `idx_rvbuyer_state`(`reverse_buyer_id`,`order_state`,`gmt_create`) USING BTREE
;

ALTER TABLE `trade_order_0001`
	ADD COLUMN `reverse_buyer_id` varchar(32) NULL COMMENT '逆序的买家ID' AFTER `buyer_id`,
	ADD KEY `idx_rvbuyer_state`(`reverse_buyer_id`,`order_state`,`gmt_create`) USING BTREE
;

ALTER TABLE `trade_order_0002`
	ADD COLUMN `reverse_buyer_id` varchar(32) NULL COMMENT '逆序的买家ID' AFTER `buyer_id`,
	ADD KEY `idx_rvbuyer_state`(`reverse_buyer_id`,`order_state`,`gmt_create`) USING BTREE
;

ALTER TABLE `trade_order_0003`
	ADD COLUMN `reverse_buyer_id` varchar(32) NULL COMMENT '逆序的买家ID' AFTER `buyer_id`,
	ADD KEY `idx_rvbuyer_state`(`reverse_buyer_id`,`order_state`,`gmt_create`) USING BTREE
;

update trade_order_0000 set `reverse_buyer_id`  = REVERSE(`buyer_id` );
update trade_order_0001 set `reverse_buyer_id`  = REVERSE(`buyer_id` );
update trade_order_0003 set `reverse_buyer_id`  = REVERSE(`buyer_id` );
update trade_order_0002 set `reverse_buyer_id`  = REVERSE(`buyer_id` );


# 2024-08-25 新增refund_order表

/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = refund_order   */
/******************************************/
CREATE TABLE `refund_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `refund_order_id` varchar(32) NOT NULL COMMENT '支付单号',
  `identifier` varchar(128) NOT NULL COMMENT '幂等号',
  `pay_order_id` varchar(32) NOT NULL COMMENT '支付单号',
  `pay_channel_stream_id` varchar(64) DEFAULT NULL COMMENT '支付的渠道流水号',
  `paid_amount` decimal(18,6) DEFAULT NULL COMMENT '已支付金额',
  `payer_id` varchar(32) NOT NULL COMMENT '付款方iD',
  `payer_type` varchar(32) NOT NULL COMMENT '付款方类型',
  `payee_id` varchar(32) NOT NULL COMMENT '收款方id',
  `payee_type` varchar(32) NOT NULL COMMENT '收款方类型',
  `apply_refund_amount` decimal(18,6) NOT NULL COMMENT '申请退款金额',
  `refunded_amount` decimal(18,6) DEFAULT NULL COMMENT '退款成功金额',
  `refund_channel_stream_id` varchar(64) DEFAULT NULL COMMENT '退款的渠道流水号',
  `refund_channel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '退款方式',
  `memo` varchar(512) DEFAULT NULL COMMENT '备注',
  `refund_order_state` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '退款单状态',
  `refund_succeed_time` datetime DEFAULT NULL COMMENT '退款成功时间',
  `deleted` tinyint DEFAULT NULL COMMENT '逻辑删除标识',
  `lock_version` int DEFAULT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  KEY `idx_pay_order` (`pay_order_id`) USING BTREE,
  KEY `uk_identifier` (`identifier`,`pay_order_id`,`refund_channel`),
  KEY `idx_refund_order` (`refund_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
;
