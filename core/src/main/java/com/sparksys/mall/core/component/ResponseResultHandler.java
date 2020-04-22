package com.sparksys.mall.core.component;

import com.sparksys.mall.core.annotation.ResponseResult;
import com.sparksys.mall.core.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

/**
 * 中文类名: ResponseResult处理
 * 中文描述: 判断是否需要返回值包装，如果需要就直接包装
 *
 * @author zhouxinlei
 * @date 2019-09-26 10:18:14
 */
@Slf4j
@ControllerAdvice(annotations = ResponseResult.class)
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    private static final Class[] ANNOS = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(ANNOS).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<?
            extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("进入返回体{} 重写格式 处理中", body, "-----------------------------");
        if (body instanceof Boolean) {
            boolean data = (Boolean) body;
            if (!data) {
                return ApiResult.failed(body);
            }
        }
        return ApiResult.success(body);
    }
}
