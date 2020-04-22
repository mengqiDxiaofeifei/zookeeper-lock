package com.sparksys.mall.admin.config;

import com.sparksys.mall.admin.component.UmsAdminArgumentResolver;
import com.sparksys.mall.admin.util.HttpMessageConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

/**
 * 中文类名: WebConfig全局配置
 * 中文描述: WebConfig全局配置
 *
 * @author zhouxinlei
 * @date 2019-10-03 09:01:11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UmsAdminArgumentResolver adminArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminArgumentResolver);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }
        converters.add(HttpMessageConverterUtils.fastJsonHttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
}
