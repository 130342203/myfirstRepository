package com.ck.framework;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author ron
 *         2016/5/18.
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> aClass) {
        check();
        return applicationContext.getBean(aClass);
    }

    public static <T> T getBean(String name) {
        check();
        return (T) applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> aClass) {
        check();
        return applicationContext.getBeansOfType(aClass);

    }
    public static  Map<String, Object>  getBeansWithAnnotation(Class<? extends Annotation> anno) {
        check();
        return  applicationContext.getBeansWithAnnotation(anno);
    }

    private static void check() throws IllegalStateException{
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext is not injected.");
        }
    }
}
