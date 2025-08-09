package cn.hollis.nft.turbo.chain.infrastructure.utils;

import cn.hollis.nft.turbo.chain.domain.entity.RequestBody;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.google.common.collect.Maps;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文昌链工具类
 *
 * @author hollis
 */
public class WenChangChainUtils {

    public static Map<String,String> configureHeaders(String signature, Long timestamp, String apiKey) {
        Map headers = Maps.newHashMapWithExpectedSize(3);
        headers.put("X-Api-Key", apiKey);
        headers.put("X-Timestamp", timestamp.toString());
        headers.put("X-Signature", signature);
        return headers;
    }

    public static HttpHeaders configureHeaders(HttpHeaders headers, String signature, Long timestamp, String apiKey) {
        headers.add("X-Api-Key", apiKey);
        headers.add("X-Timestamp", timestamp.toString());
        headers.add("X-Signature", signature);
        return headers;
    }

    /**
     * 对请求参数进行签名处理
     *
     * @param path      请求路径，仅截取域名后及 Query 参数前部分，例："/v3/accounts";
     * @param body      Body 参数，例："{\"count\": 1, \"operation_id\":
     *                  \"random_string\"}"，需转为 Map 格式
     * @param timestamp 当前时间戳（毫秒），例：1645551123703
     * @param apiSecret 应用方的 API Secret，例："L2w4m0e2S0k5v0J7Q4l9g0T0B197m3eA"
     * @return 返回签名结果
     */
    public static String signRequest(String path, RequestBody body, long timestamp,
        String apiSecret) {
        Map<String, Object> paramsMap = Maps.newHashMapWithExpectedSize(1);

        paramsMap.put("path_url", path);

        if (body != null ) {
            Map<String, Object> bodyMap = JSON.parseObject(JSON.toJSONString(body), Map.class);
            bodyMap.forEach((key, value) -> paramsMap.put("body_" + key, value));
        }

        //  将请求参数序列化为排序后的 JSON 字符串
        String jsonStr = JSON.toJSONString(paramsMap, JSONWriter.Feature.MapSortField);

        // 执行签名
        String signature = DigestUtil.sha256Hex(jsonStr + timestamp + apiSecret);

        return signature;
    }

}
