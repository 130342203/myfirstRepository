package com.ck.config;

//import com.ck.framework.filter.OwnFilter;
import com.ck.framework.interceptor.OwnInterceptor;
import org.apache.catalina.ssi.SSIFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
public class WebConfig extends WebMvcConfigurationSupport{

    @Override
    public void addViewControllers(ViewControllerRegistry controllerRegistry){
        controllerRegistry.addViewController("/").setViewName("forward:/html/welcome.html");
        controllerRegistry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(controllerRegistry);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/").addResourceLocations("/**");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(ownInterceptor());
    }

    @Bean
    public OwnInterceptor ownInterceptor(){
        return new OwnInterceptor();
    }
    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver viewResolver =  new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }
   /* @Bean
    public OwnFilter ownFilter(){
        return new OwnFilter();
    }*/
    /*@Bean
    public SSIFilter ssiFilter(){
        return new SSIFilter();
    }*/
}
