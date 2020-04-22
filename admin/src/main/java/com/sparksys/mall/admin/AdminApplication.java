package com.sparksys.mall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 中文类名：admin启动类
 * 概要说明：admin启动类
 *
 * @author zhouxinlei
 * @date 2019/5/28 0028
 */
@SpringBootApplication(scanBasePackages = "com.sparksys.mall")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
