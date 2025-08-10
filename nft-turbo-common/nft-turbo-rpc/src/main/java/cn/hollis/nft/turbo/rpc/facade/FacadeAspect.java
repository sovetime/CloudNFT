package cn.hollis.nft.turbo.rpc.facade;

import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.SystemException;
import cn.hollis.nft.turbo.base.response.BaseResponse;
import cn.hollis.nft.turbo.base.response.ResponseCode;
import cn.hollis.nft.turbo.base.utils.BeanValidator;
import com.alibaba.fastjson2.JSON;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


// FacadeAspect 是一个切面类，用于统一处理带有 @Facade 注解的方法调用
//它提供了参数校验、方法执行、日志记录、响应补全以及异常处理等功能。
@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class FacadeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacadeAspect.class);

    //切面环绕通知方法，用于处理标注了 @Facade 注解的方法
    //主要功能：参数校验，方法执行，响应补全，日志记录，异常处理及失败响应构造。
    @Around("@annotation(cn.hollis.nft.turbo.rpc.facade.Facade)")
    public Object facade(ProceedingJoinPoint pjp) throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();

        Class returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();

        // 循环遍历所有参数，进行参数校验
        for (Object parameter : args) {
            try {
                BeanValidator.validateObject(parameter);
            } catch (ValidationException e) {
                printLog(stopWatch, method, args, "failed to validate", null, e);
                return getFailedResponse(returnType, e);
            }
        }

        try {
            // 目标方法执行
            Object response = pjp.proceed();
            enrichObject(response);
            printLog(stopWatch, method, args, "end to execute", response, null);
            return response;
        } catch (Throwable throwable) {
            // 如果执行异常，则返回一个失败的response
            printLog(stopWatch, method, args, "failed to execute", null, throwable);
            return getFailedResponse(returnType, throwable);
        }
    }

    //打印方法执行日志,包含方法名、执行耗时、参数、响应结果或异常信息等
    private void printLog(StopWatch stopWatch, Method method, Object[] args, String action, Object response,
                          Throwable throwable) {
        try {
            // 因为此处有 JSON.toJSONString，可能会有异常，需要进行捕获，避免影响主干流程
            LOGGER.info(getInfoMessage(action, stopWatch, method, args, response, throwable), throwable);
        } catch (Exception e1) {
            LOGGER.error("log failed", e1);
        }
    }

    //构造统一格式的日志信息字符串,包含方法名、执行时间、参数、响应结果、异常信息等。
    private String getInfoMessage(String action, StopWatch stopWatch, Method method, Object[] args,
                                                            Object response, Throwable exception) {

        StringBuilder stringBuilder = new StringBuilder(action);
        stringBuilder.append(" ,method = ");
        stringBuilder.append(method.getName());
        stringBuilder.append(" ,cost = ");
        stringBuilder.append(stopWatch.getTime()).append(" ms");
        if (response instanceof BaseResponse) {
            stringBuilder.append(" ,success = ");
            stringBuilder.append(((BaseResponse) response).getSuccess());
        }
        if (exception != null) {
            stringBuilder.append(" ,success = ");
            stringBuilder.append(false);
        }
        stringBuilder.append(" ,args = ");
        stringBuilder.append(JSON.toJSONString(Arrays.toString(args)));

        if (response != null) {
            stringBuilder.append(" ,resp = ");
            stringBuilder.append(JSON.toJSONString(response));
        }

        if (exception != null) {
            stringBuilder.append(" ,exception = ");
            stringBuilder.append(exception.getMessage());
        }

        if (response instanceof BaseResponse) {
            BaseResponse baseResponse = (BaseResponse) response;
            if (!baseResponse.getSuccess()) {
                stringBuilder.append(" , execute_failed");
            }
        }

        return stringBuilder.toString();
    }


    //补全响应对象中的 code 和 message 字段
    //如果响应成功但未设置 code，则默认为 SUCCESS
    //如果响应失败但未设置 code，则默认为 BIZ_ERROR
    private void enrichObject(Object response) {
        if (response instanceof BaseResponse) {
            if (((BaseResponse) response).getSuccess()) {
                // 如果状态是成功的，需要将未设置的responseCode设置成SUCCESS
                if (StringUtils.isEmpty(((BaseResponse) response).getResponseCode())) {
                    ((BaseResponse) response).setResponseCode(ResponseCode.SUCCESS.name());
                }
            } else {
                // 如果状态是失败的，需要将未设置的responseCode设置成BIZ_ERROR
                if (StringUtils.isEmpty(((BaseResponse) response).getResponseCode())) {
                    ((BaseResponse) response).setResponseCode(ResponseCode.BIZ_ERROR.name());
                }
            }
        }
    }


    //构造一个通用的失败响应对象.根据异常类型设置对应的错误码和错误信息。
    private Object getFailedResponse(Class returnType, Throwable throwable)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        // 如果返回值的类型为BaseResponse 的子类，则创建一个通用的失败响应
        if (returnType.getDeclaredConstructor().newInstance() instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) returnType.getDeclaredConstructor().newInstance();
            response.setSuccess(false);
            if (throwable instanceof BizException bizException) {
                response.setResponseMessage(bizException.getErrorCode().getMessage());
                response.setResponseCode(bizException.getErrorCode().getCode());
            } else if (throwable instanceof SystemException systemException) {
                response.setResponseMessage(systemException.getErrorCode().getMessage());
                response.setResponseCode(systemException.getErrorCode().getCode());
            } else {
                response.setResponseMessage(throwable.toString());
                response.setResponseCode(ResponseCode.BIZ_ERROR.name());
            }

            return response;
        }

        LOGGER.error(
                "failed to getFailedResponse , returnType (" + returnType + ") is not instanceof BaseResponse");
        return null;
    }
}

