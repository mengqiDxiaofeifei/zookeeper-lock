package com.sparksys.mall.admin.component;

import com.sparksys.mall.admin.util.SecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 中文类名: 当访问接口没有权限时，自定义的返回结果
 * 中文描述: 当访问接口没有权限时，自定义的返回结果
 *
 * @author zhouxinlei
 * @date 2019-10-15 11:38:25
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        SecurityUtils.forbidden(response);
    }
}
