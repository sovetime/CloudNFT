package cn.hollis.nft.turbo.inventory.domain.service.impl;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.inventory.domain.response.InventoryResponse;
import cn.hollis.nft.turbo.inventory.domain.service.InventoryService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.redisson.client.codec.IntegerCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cn.hollis.nft.turbo.base.response.ResponseCode.BIZ_ERROR;
import static cn.hollis.nft.turbo.base.response.ResponseCode.DUPLICATED;


//库存服务通用实现-基于Redis
public abstract class AbstractInventoryRedisService implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractInventoryRedisService.class);

    @Autowired
    private RedissonClient redissonClient;

    public static final String ERROR_CODE_INVENTORY_NOT_ENOUGH = "INVENTORY_NOT_ENOUGH";
    public static final String ERROR_CODE_INVENTORY_IS_ZERO = "INVENTORY_IS_ZERO";
    public static final String ERROR_CODE_KEY_NOT_FOUND = "KEY_NOT_FOUND";
    public static final String ERROR_CODE_OPERATION_ALREADY_EXECUTED = "OPERATION_ALREADY_EXECUTED";

    //库存初始化
    @Override
    public InventoryResponse init(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();

        //库存存在
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            inventoryResponse.setSuccess(true);
            inventoryResponse.setResponseCode(DUPLICATED.name());
            return inventoryResponse;
        }

        redissonClient.getBucket(getCacheKey(request)).set(request.getInventory());
        inventoryResponse.setSuccess(true);
        inventoryResponse.setGoodsId(request.getGoodsId());
        inventoryResponse.setGoodsType(request.getGoodsType());
        inventoryResponse.setIdentifier(request.getIdentifier());
        inventoryResponse.setInventory(request.getInventory());
        return inventoryResponse;
    }

    @Override
    public Integer getInventory(InventoryRequest request) {
        Integer stock = (Integer) redissonClient.getBucket(getCacheKey(request), IntegerCodec.INSTANCE).get();
        return stock;
    }

    //库存扣减
    @Override
    public InventoryResponse decrease(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();

        //KEYS[1]= 库存key -> clc:inventory:10000
        //KEYS[2] = 流水的 key -> clc:inventory:stream:10000
        //ARGV[1] = 本次要扣减的库存数 -> 1
        //AGRV[2] = 本次扣减的唯一编号 -> id_11232132132131
        String luaScript = """
                -- 检查操作是否已经执行过，通过检查哈希表(KEYS[2])中是否存在标识(ARGV[2])
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                
                -- 获取当前库存值
                local current = redis.call('get', KEYS[1])
                
                -- 库存键不存在，值不是数字，值为0，库存小于请求扣减数量，返回对应的报错信息
                if current == false then
                    return redis.error_reply('KEY_NOT_FOUND')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                if tonumber(current) == 0 then
                    return redis.error_reply('INVENTORY_IS_ZERO')
                end
                if tonumber(current) < tonumber(ARGV[1]) then
                    return redis.error_reply('INVENTORY_NOT_ENOUGH')
                end
                
                -- 计算新的库存值 当前值-需要扣减的数量
                local new = tonumber(current) - tonumber(ARGV[1])
                -- 设置新的库存值
                redis.call('set', KEYS[1], tostring(new))
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                
                -- 使用哈希结构存储操作日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "decrease",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
               
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript().eval(
                    RScript.Mode.READ_WRITE,            //读写模式
                    luaScript,                          //执行的lua脚本
                    RScript.ReturnType.INTEGER,         //返回类型
                    Arrays.asList(getCacheKey(request), //KEYS[1]
                    getCacheStreamKey(request)),        //KEYS[2]
                    request.getInventory(),     //ARGV[1]
                    "DECREASE_" + request.getIdentifier()//ARGV[2]
            )).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            logger.error("decrease error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_INVENTORY_NOT_ENOUGH)) {
                inventoryResponse.setResponseCode(ERROR_CODE_INVENTORY_NOT_ENOUGH);
            } else if (e.getMessage().startsWith(ERROR_CODE_INVENTORY_IS_ZERO)) {
                inventoryResponse.setResponseCode(ERROR_CODE_INVENTORY_IS_ZERO);
            } else if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    //
    @Override
    public String getInventoryDecreaseLog(InventoryRequest request) {
        String luaScript = """
                local jsonString = redis.call('hget', KEYS[1], ARGV[1])
                return jsonString
                """;

        String stream = redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                luaScript,
                RScript.ReturnType.STATUS,
                Arrays.asList(getCacheStreamKey(request)), "DECREASE_" + request.getIdentifier());
        return stream;
    }

    @Override
    public List<String> getInventoryDecreaseLogs(InventoryRequest request) {
        String luaScript = """
                local jsonString = redis.call('hvals', KEYS[1])
                return jsonString
                """;

        List<String> stream = redissonClient.getScript().eval(RScript.Mode.READ_ONLY,
                luaScript,
                RScript.ReturnType.STATUS,
                Arrays.asList(getCacheStreamKey(request)),
                Collections.emptyList());
        return stream;
    }

    @Override
    public Long removeInventoryDecreaseLog(InventoryRequest request) {
        String luaScript = """
                local jsonString = redis.call('hdel', KEYS[1], ARGV[1])
                return jsonString
                """;

        Long stream = redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                luaScript,
                RScript.ReturnType.INTEGER,
                Arrays.asList(getCacheStreamKey(request)), "DECREASE_" + request.getIdentifier());
        return stream;
    }

    @Override
    public InventoryResponse increase(InventoryRequest request) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        String luaScript = """
                if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then
                    return redis.error_reply('OPERATION_ALREADY_EXECUTED')
                end
                                
                local current = redis.call('get', KEYS[1])
                if current == false then
                    return redis.error_reply('key not found')
                end
                if tonumber(current) == nil then
                    return redis.error_reply('current value is not a number')
                end
                                
                local new = (current == nil and 0 or tonumber(current)) + tonumber(ARGV[1])
                redis.call('set', KEYS[1], tostring(new))
                                
                -- 获取Redis服务器的当前时间（秒和微秒）
                local time = redis.call("time")
                -- 转换为毫秒级时间戳
                local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)
                                
                -- 使用哈希结构存储日志
                redis.call('hset', KEYS[2], ARGV[2], cjson.encode({
                    action = "increase",
                    from = current,
                    to = new,
                    change = ARGV[1],
                    by = ARGV[2],
                    timestamp = currentTimeMillis
                }))
                                
                return new
                """;

        try {
            Integer result = ((Long) redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                    luaScript,
                    RScript.ReturnType.INTEGER,
                    Arrays.asList(getCacheKey(request), getCacheStreamKey(request)),
                    request.getInventory(), "INCREASE_" + request.getIdentifier())).intValue();

            inventoryResponse.setSuccess(true);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            inventoryResponse.setInventory(result);
            return inventoryResponse;

        } catch (RedisException e) {
            logger.error("increase error , goodsId = {} , identifier = {} ,", request.getGoodsId(), request.getIdentifier(), e);
            inventoryResponse.setSuccess(false);
            inventoryResponse.setGoodsId(request.getGoodsId());
            inventoryResponse.setGoodsType(request.getGoodsType());
            inventoryResponse.setIdentifier(request.getIdentifier());
            if (e.getMessage().startsWith(ERROR_CODE_KEY_NOT_FOUND)) {
                inventoryResponse.setResponseCode(ERROR_CODE_KEY_NOT_FOUND);
            } else if (e.getMessage().startsWith(ERROR_CODE_OPERATION_ALREADY_EXECUTED)) {
                inventoryResponse.setResponseCode(ERROR_CODE_OPERATION_ALREADY_EXECUTED);
                inventoryResponse.setSuccess(true);
            } else {
                inventoryResponse.setResponseCode(BIZ_ERROR.name());
            }
            inventoryResponse.setResponseMessage(e.getMessage());

            return inventoryResponse;
        }
    }

    @Override
    public void invalid(InventoryRequest request) {
        if (redissonClient.getBucket(getCacheKey(request)).isExists()) {
            redissonClient.getBucket(getCacheKey(request)).delete();
        }

        if (redissonClient.getBucket(getCacheStreamKey(request)).isExists()) {
            // 让流水记录的过期时间设置为24小时后，这样可以避免流水记录立即过期，对账出现问题
            redissonClient.getBucket(getCacheStreamKey(request)).expire(Instant.now().plus(24, ChronoUnit.HOURS));
        }
    }

    //获取库存缓存的key
    protected abstract String getCacheKey(InventoryRequest request);

    //获取库存流水缓存的key
    protected abstract String getCacheStreamKey(InventoryRequest request);
}
