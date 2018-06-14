/*
package com.ck.config;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfigTest  implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry controllerRegistry){
        controllerRegistry.addViewController("/html").setViewName("forward:/welcome.html");
        controllerRegistry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/").addResourceLocations("/**");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }
}
*/
