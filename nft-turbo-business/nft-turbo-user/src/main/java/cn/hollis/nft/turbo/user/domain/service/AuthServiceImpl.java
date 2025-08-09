package cn.hollis.nft.turbo.user.domain.service;

import cn.hollis.nft.turbo.base.utils.HttpUtils;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * 认证服务
 *
 * @author hollis
 */
@Slf4j
public class AuthServiceImpl implements AuthService {
    private String host;

    private String path;

    private String appcode;

    private static final String STATE = "state";

    public AuthServiceImpl(String host, String path, String appcode) {
        this.host = host;
        this.path = path;
        this.appcode = appcode;
    }

    @Override
    public boolean checkAuth(String realName, String idCard) {
        String method = "POST";
        Map<String, String> headers = Maps.newHashMapWithExpectedSize(2);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = Maps.newHashMapWithExpectedSize(2);
        Map<String, String> bodys = Maps.newHashMapWithExpectedSize(2);
        bodys.put("id_number", idCard);
        bodys.put("name", realName);

        try {
            var response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            Map<String, Object> resultMap = JSON.parseObject(EntityUtils.toString(response.getEntity()), Map.class);
            log.info("auth result : " + resultMap);
            if ((Integer)resultMap.get(STATE) == 1) {
                return true;
            }
        } catch (Exception e) {
            log.error("checkAuth error realName=" + realName, e);
        }
        return false;

    }

}
