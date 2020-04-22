package com.sparksys.mall.core.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 中文类名: controller日志切面 中文描述: controller日志切面
 *
 * @author zhouxinlei
 * @date 2019-09-10 13:04:48
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.sparksys.mall.*.controller..*.*(..))")
    public void pointCut() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-10 10:40:49
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer sb = new StringBuffer("==> Aop log before method request");
        //URL
        sb.append("\n\tUrl:" + request.getRequestURL().toString());
        //HTTP_METHOD
        sb.append("\n\tAction:" + request.getMethod());
        //IP:
        sb.append("\n\tIp:" + getIpAddress(request));
        //METHOD:
        sb.append("\n\tMethod:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //ARGS
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isNotEmpty(args)) {
            int index = 0;
            for (Object object : args) {
                if (object != null) {
                    index++;
                    if (object instanceof ServletRequest
                            || object instanceof ServletResponse
                            || object instanceof MultipartFile) {
                        continue;
                    }
                    sb.append("\n\tParameter" + index + ":" + JSON.toJSONString(object));
                }
            }
        }
        log.info(sb.toString());
    }

    /**
     * 后置通知
     *
     * @param joinPoint
     * @param keys
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-10 10:59:25
     */
    @AfterReturning(pointcut = "pointCut()", returning = "keys")
    public void after(JoinPoint joinPoint, Object keys) {
        log.info("<== Aop log after method ending \n\tMethod: {}.{}\n\tResult: {}", joinPoint.getTarget().getClass().getName()
                , joinPoint.getSignature().getName(), JSON.toJSON(keys));
    }

    /**
     * 异常通知，拦截记录异常日志
     *
     * @param joinPoint
     * @param e
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-10 10:59:37
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String strLog = "<== Aop log after throw method : "
                + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + "  Exception:\n" + e.getLocalizedMessage();
        log.error(strLog);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址。
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串
     *
     * @param request
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-10 13:18:13
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }
}
