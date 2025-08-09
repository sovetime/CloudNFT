package cn.hollis.nft.turbo.web.filter;

import cn.hollis.nft.turbo.web.util.TokenUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;


public class TokenFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    public static final ThreadLocal<String> TOKEN_THREAD_LOCAL = new ThreadLocal<>();

    public static final ThreadLocal<Boolean> STRESS_THREAD_LOCAL = new ThreadLocal<>();

    private static final String HEADER_VALUE_NULL = "null";

    private static final String HEADER_VALUE_UNDEFINED = "undefined";

    private RedissonClient redissonClient;

    public TokenFilter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化，可选实现
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 从请求头中获取Token
            String token = httpRequest.getHeader("Authorization");
            Boolean isStress = BooleanUtils.toBoolean(httpRequest.getHeader("isStress"));

            if (token == null || HEADER_VALUE_NULL.equals(token) || HEADER_VALUE_UNDEFINED.equals(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("No Token Found ...");
                logger.error("no token found in header , pls check!");
                return;
            }

            // 校验Token的有效性
            boolean isValid = checkTokenValidity(token, isStress);

            if (!isValid) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Invalid or expired token");
                logger.error("token validate failed , pls check!");
                return;
            }

            // Token有效，继续执行其他过滤器链
            chain.doFilter(request, response);
        } finally {
            TOKEN_THREAD_LOCAL.remove();
            STRESS_THREAD_LOCAL.remove();
        }
    }

    /**
     * <p>
     * 1、把加密后的token解密
     * 2、把加密后的token的value转成key
     * 3、去redis按照key查询并且判断value是否一致
     * 4、如果不一致，抛异常
     * 5、如果一致，则删除这个key
     * </p>
     *
     * @param token
     * @param isStress
     * @return
     */
    private boolean checkTokenValidity(String token, Boolean isStress) {
        String result;
        if (isStress) {
            //如果是压测，则生成一个随机数，模拟 token
            result = UUID.randomUUID().toString();
            STRESS_THREAD_LOCAL.set(isStress);
        }else{
            String tokenKey = TokenUtil.getTokenKeyByValue(token);

            String luaScript = """
                local value = redis.call('GET', KEYS[1])
                
                if value ~= ARGV[1] then
                    return redis.error_reply('token not valid')
                end
                
                redis.call('DEL', KEYS[1])
                return value""";

            try {
                /// 6.2.3以上可以直接使用GETDEL命令
                /// String value = (String) redisTemplate.opsForValue().getAndDelete(token);
                result = (String) redissonClient.getScript().eval(RScript.Mode.READ_WRITE,
                        luaScript,
                        RScript.ReturnType.STATUS,
                        Arrays.asList(tokenKey), token);
            } catch (RedisException e) {
                logger.error("check token failed", e);
                return false;
            }
        }

        TOKEN_THREAD_LOCAL.set(result);
        return result != null;
    }

    @Override
    public void destroy() {
    }
}
