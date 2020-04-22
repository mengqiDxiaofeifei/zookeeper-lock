package com.sparksys.mall.admin.component;

import com.sparksys.mall.admin.util.SecurityUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouxinlei
 * @中文类名: 当未登录或者token失效访问接口时，自定义的返回结果
 * @中文描述: 当未登录或者token失效访问接口时，自定义的返回结果
 * @date 2019-10-15 10:27:25
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        SecurityUtils.unauthorized(response);
    }
}
