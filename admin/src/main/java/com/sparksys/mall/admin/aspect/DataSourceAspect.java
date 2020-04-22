package com.sparksys.mall.admin.aspect;

import com.sparksys.mall.core.component.DataContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 中文类名：主从切换Aspect
 * 概要说明：主从切换Aspect
 *
 * @author zhouxinlei
 * @date 2019/5/29 0029
 */
@Aspect
@Component
public class DataSourceAspect {
    @Pointcut("!@annotation(com.sparksys.mall.admin.annotation.Master) " +
            "&& (execution(* com.sparksys.mall.admin.dao..*.select*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.list*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.find*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.sparksys.mall.admin.annotation.Master) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.insert*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.reduce*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.update*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.save*(..)) " +
            "|| execution(* com.sparksys.mall.admin.dao..*.delete*(..)) ")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DataContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DataContextHolder.master();
    }

}
