package com.xixi.person.talk.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/20 12:32
 * @Description:
 */
@Configuration
//@EnableWebMvc 这个注解可以使静态资源被拦懒
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}