package com.sparksys.mall.admin.component;

import com.alibaba.fastjson.JSON;
import com.sparksys.mall.admin.constant.RedisConstant;
import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.util.RedisUtil;
import com.sparksys.mall.core.constant.CoreConstant;
import com.sparksys.mall.core.exception.ApiErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 中文类名: UmsAdminBean全局获取
 * 中文描述: UmsAdminBean全局获取
 *
 * @author zhouxinlei
 * @date 2019-10-03 08:51:03
 */
@Component
@Slf4j
public class UmsAdminArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == UmsAdminBean.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String header = servletRequest.getHeader(CoreConstant.JwtTokenConstant.JWT_TOKEN_HEADER);
        String accessToken = StringUtils.removeStart(header, CoreConstant.JwtTokenConstant.JWT_TOKEN_HEAD);
        log.info("accessToken is {}", accessToken);
        String adminJson = redisUtil.get(RedisConstant.LoginKeyConstant.USER + accessToken);
        if (StringUtils.isEmpty(adminJson)) {
            throw new ApiErrorException("暂未登录或token已过期");
        }
        UmsAdminBean umsAdminBean = JSON.parseObject(adminJson, UmsAdminBean.class);
        return umsAdminBean;
    }
}
