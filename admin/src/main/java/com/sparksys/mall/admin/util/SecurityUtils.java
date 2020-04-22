package com.sparksys.mall.admin.util;


import com.alibaba.fastjson.JSON;
import com.sparksys.mall.core.constant.CoreConstant;
import com.sparksys.mall.core.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 中文类名: SecurityUtils工具类
 * 中文描述: SecurityUtils工具类
 *
 * @author zhouxinlei
 * @date 2019-09-20 10:07:34
 */
@Slf4j
public class SecurityUtils {

    public static String getAuthHeader(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader(CoreConstant.JwtTokenConstant.JWT_TOKEN_HEADER);
        return StringUtils.removeStart(header, CoreConstant.JwtTokenConstant.JWT_TOKEN_HEAD);
    }

    public static void unauthorized(HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSON.toJSON(ApiResult.unauthorized()));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forbidden(HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSON.toJSON(ApiResult.forbidden()));
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
