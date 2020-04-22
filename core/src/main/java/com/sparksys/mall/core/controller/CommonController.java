package com.sparksys.mall.core.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 中文类名：公共controller
 * 概要说明：公共controller
 *
 * @author zhouxinlei
 * @date 2019/5/26 0026
 */
public class CommonController implements Serializable {

    private static final long serialVersionUID = -2821153920460160170L;

    /**
     * 定义当前线程HttpServletRequest
     */
    public static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 定义当前线程HttpServletResponse
     */
    public static final ThreadLocal<HttpServletResponse> RESPONSE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 初始化request
     *
     * @param request
     * @return void
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    @ModelAttribute
    public final void initRequest(HttpServletRequest request) {
        REQUEST_THREAD_LOCAL.remove();
        REQUEST_THREAD_LOCAL.set(request);
    }

    /**
     * 初始化response
     *
     * @param response
     * @return void
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    @ModelAttribute
    public final void initResponse(HttpServletResponse response) {
        RESPONSE_THREAD_LOCAL.remove();
        RESPONSE_THREAD_LOCAL.set(response);
    }

    /**
     * 获取当前线程的response对象
     *
     * @param
     * @return javax.servlet.http.HttpServletResponse
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    protected final HttpServletResponse getResponse() {
        return RESPONSE_THREAD_LOCAL.get();
    }

    /**
     * 获取当前线程的request对象
     *
     * @param
     * @return javax.servlet.http.HttpServletRequest
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    protected final HttpServletRequest getRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }

}
