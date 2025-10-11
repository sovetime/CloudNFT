package cn.time.nft.turbo.rpc.facade;

import cn.time.nft.turbo.base.exception.BizException;
import cn.time.nft.turbo.base.exception.SystemException;
import cn.time.nft.turbo.base.response.BaseResponse;
import cn.time.nft.turbo.base.response.ResponseCode;
import cn.time.nft.turbo.base.utils.BeanValidator;
import com.alibaba.fastjson2.JSON;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


//它提供了参数校验、方法执行、日志记录、响应补全以及异常处理等功能。
@Aspect
@Component
@Slf4j
@Order(Integer.MIN_VALUE)
public class FacadeAspect {

    //主要功能：参数校验，方法执行，响应补全，日志记录，异常处理及失败响应构造。
    @Around("@annotation(cn.time.nft.turbo.rpc.facade.Facade)")
    public Object facade(ProceedingJoinPoint pjp) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //获取目标方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        // 获取参数
        Object[] args = pjp.getArgs();
        // 获取返回值类型
        Class returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();

        // 循环遍历所有参数，进行参数校验
        for (Object parameter : args) {
            try {
                //使用参数校验工具进行参数校验
                BeanValidator.validateObject(parameter);
            } catch (ValidationException e) {
                //打印错误日志
                printErrorLog(stopWatch, method, args, "failed to validate", null, e);
                return getFailedResponse(returnType, e);
            }
        }

        try {
            // 目标方法执行
            Object response = pjp.proceed();
            // 补全响应对象中的 code 和 message 字段
            enrichObject(response);
            // 打印方法执行日志
            printInfoLog(stopWatch, method, args, "end to execute", response, null);
            return response;
        } catch (Throwable throwable) {
            // 如果执行异常，则返回一个失败的response
            printInfoLog(stopWatch, method, args, "end to execute",null, null);
            return getFailedResponse(returnType, throwable);
        }
    }

    //打印方法执行日志,包含方法名、执行耗时、参数、响应结果或异常信息等
    private void printInfoLog(StopWatch stopWatch, Method method, Object[] args, String action, Object response, Throwable throwable) {
        try {
            // 因为此处有 JSON.toJSONString，可能会有异常，需要进行捕获，避免影响主干流程
            log.info(getInfoMessage(action, stopWatch, method, args, response, throwable), throwable);
        } catch (Exception e1) {
            log.error("log failed", e1);
        }
    }

    //日志打印
    private void printErrorLog(StopWatch stopWatch, Method method, Object[] args, String action, Object response, Throwable throwable) {
        try {
            //因为此处有JSON.toJSONString，可能会有异常，需要进行捕获，避免影响主干流程
            log.error(getInfoMessage(action, stopWatch, method, args, response, throwable), throwable);
            // 如果校验失败，则返回一个失败的response
        } catch (Exception e1) {
            log.error("log failed", e1);
        }
    }

    //构造统一格式的日志信息字符串,包含方法名、执行时间、参数、响应结果、异常信息等。
    private String getInfoMessage(String action, StopWatch stopWatch, Method method, Object[] args, Object response, Throwable exception) {
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

        log.error("failed to getFailedResponse , returnType (" + returnType + ") is not instanceof BaseResponse");
        return null;
    }
}

