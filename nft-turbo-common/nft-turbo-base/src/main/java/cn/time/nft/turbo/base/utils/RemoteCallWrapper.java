package cn.time.nft.turbo.base.utils;

import cn.time.nft.turbo.base.exception.RemoteCallException;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static cn.time.nft.turbo.base.exception.BizErrorCode.REMOTE_CALL_RESPONSE_IS_FAILED;
import static cn.time.nft.turbo.base.exception.BizErrorCode.REMOTE_CALL_RESPONSE_IS_NULL;


//远程方法调用的包装工具类
@Slf4j
public class RemoteCallWrapper {

    //ImmutableSet 是Google Guava 的一个不可变集合类，一旦创建就不能再增删改元素
    private static ImmutableSet<String> SUCCESS_CHECK_METHOD = ImmutableSet.of("isSuccess", "isSucceeded", "getSuccess");

    private static ImmutableSet<String> SUCCESS_CODE_METHOD = ImmutableSet.of("getResponseCode");

    private static ImmutableSet<String> SUCCESS_CODE = ImmutableSet.of("SUCCESS", "DUPLICATE", "DUPLICATED_REQUEST");

    public static <T, R> R call(Function<T, R> function, T request, boolean checkResponse) {
        return call(function, request, request.getClass().getSimpleName(), checkResponse, false);
    }

    public static <T, R> R call(Function<T, R> function, T request) {
        return call(function, request, request.getClass().getSimpleName(), true, false);
    }

    public static <T, R> R call(Function<T, R> function, T request, String requestName) {
        return call(function, request, requestName, true, false);
    }

    public static <T, R> R call(Function<T, R> function, T request, String requestName,boolean checkResponse) {
        return call(function, request, requestName, checkResponse, false);
    }

    public static <T, R> R call(Function<T, R> function, T request, boolean checkResponse, boolean checkResponseCode) {
        return call(function, request, request.getClass().getSimpleName(), checkResponse, checkResponseCode);
    }

    //进行远程调用
    public static <T, R> R call(Function<T, R> function, T request, String requestName, boolean checkResponse, boolean checkResponseCode) {
        StopWatch stopWatch = new StopWatch();

        R response = null;
        try {
            stopWatch.start();
            //调用远程方法
            response = function.apply(request);
            stopWatch.stop();

            //校验业务响应
            if (checkResponse) {
                //远程调用结果返回不能为null
                Assert.notNull(response, REMOTE_CALL_RESPONSE_IS_NULL.name());
                if (!isResponseValid(response)) {
                    log.error("Response Invalid on Remote Call request {} , response {}",
                            JSON.toJSONString(request), JSON.toJSONString(response));

                    throw new RemoteCallException(JSON.toJSONString(response), REMOTE_CALL_RESPONSE_IS_FAILED);
                }
            }

            //校验业务响应码
            if (checkResponseCode) {
                //远程调用结果返回不能为null
                Assert.notNull(response, REMOTE_CALL_RESPONSE_IS_NULL.name());
                if (!isResponseCodeValid(response)) {
                    log.error("Response code Invalid on Remote Call request {} , response {}",
                            JSON.toJSONString(request), JSON.toJSONString(response));

                    throw new RemoteCallException(JSON.toJSONString(response), REMOTE_CALL_RESPONSE_IS_FAILED);
                }
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Catch Exception on Remote Call :" + e.getMessage(), e);
            throw new IllegalArgumentException("Catch Exception on Remote Call " + e.getMessage(), e);
        } catch (Throwable e) {
            log.error("request exception {}", JSON.toJSONString(request));
            log.error("Catch Exception on Remote Call :" + e.getMessage(), e);
            throw e;
        } finally {
            if (log.isInfoEnabled()) {
                log.info("## Method={} ,## 耗时={}ms ,## [请求报文]:{},## [响应报文]:{}",
                requestName, stopWatch.getTotalTimeMillis(), JSON.toJSONString(request), JSON.toJSONString(response));
            }
        }

        return response;
    }

    //校验响应
    private static <R> boolean isResponseValid(R response) throws IllegalAccessException, InvocationTargetException {
        Method successMethod = null;

        //获取响应成功方法
        Method[] methods = response.getClass().getMethods();
        for (Method method : methods) {
            //获取方法名
            String methodName = method.getName();
            if (SUCCESS_CHECK_METHOD.contains(methodName)) {
                successMethod = method;
                break;
            }
        }
        if (successMethod == null) {
            return true;
        }

        return (Boolean) successMethod.invoke(response);
    }

    //校验响应码
    private static <R> boolean isResponseCodeValid(R response) throws IllegalAccessException, InvocationTargetException {
        Method successMethod = null;

        //获取响应成功方法
        Method[] methods = response.getClass().getMethods();
        for (Method method : methods) {
            //获取方法名
            String methodName = method.getName();
            if (SUCCESS_CODE_METHOD.contains(methodName)) {
                successMethod = method;
                break;
            }
        }
        if (successMethod == null) {
            return true;
        }

        return SUCCESS_CODE.contains(successMethod.invoke(response));
    }
}
