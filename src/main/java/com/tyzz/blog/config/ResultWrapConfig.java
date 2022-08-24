package com.tyzz.blog.config;

import com.tyzz.blog.common.Result;
import com.tyzz.blog.enums.Original;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局结果封装配置类
 */
@RestControllerAdvice
public class ResultWrapConfig implements ResponseBodyAdvice<Object> {
    /**
     * 类上不是RestController注解 || 接口方法返回Result类型 || 方法上有Original注解 都不进行封装
     * @param methodParameter the return type
     * @param aClass the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getContainingClass().isAnnotationPresent(RestController.class) ||
                !methodParameter.getParameterType().equals(Result.class) ||
                !methodParameter.hasParameterAnnotation(Original.class);
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (object instanceof String) {
            return object;
        }
        return Result.success(object);
    }
}
