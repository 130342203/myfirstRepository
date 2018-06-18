package com.ck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport{

    @Override
    public void addViewControllers(ViewControllerRegistry controllerRegistry){
        controllerRegistry.addViewController("/").setViewName("forward:/welcome.html");
        controllerRegistry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(controllerRegistry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/").addResourceLocations("/**");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
