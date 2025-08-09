package cn.hollis.nft.turbo.user.infrastructure.interceptor;

import cn.hollis.nft.turbo.api.user.response.data.InviteRankInfo;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.web.vo.Result;
import com.github.houbb.sensitive.core.api.SensitiveUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

/**
 * 脱敏响应体处理
 *
 * @author hollis
 */
@ControllerAdvice
public class SensitiveResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 只对特定类型的返回值执行处理逻辑，这里可以根据需要调整判断条件
        return Result.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果返回的对象是UserInfo/InviteRankInfo，进行脱敏处理
        if (body != null && body instanceof Result) {

            if (((Result<?>) body).getData() == null) {
                return body;
            }

            if (((Result<?>) body).getData() instanceof Collection<?>) {
                ((Result<Collection>) body).setData(SensitiveUtil.desCopyCollection((Collection) ((Result<?>) body).getData()));
                return body;
            }

            switch (((Result<?>) body).getData()) {
                case UserInfo userInfo:
                    ((Result<UserInfo>) body).setData(SensitiveUtil.desCopy(userInfo));
                    return body;
                case InviteRankInfo inviteRankInfo:
                    ((Result<InviteRankInfo>) body).setData(SensitiveUtil.desCopy(inviteRankInfo));
                    return body;
                default:
                    return body;
            }
        }
        return body;
    }
}