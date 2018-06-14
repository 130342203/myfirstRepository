package com.ck.framework.error.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author ron
 *         2016/8/9.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ErrorResource {

}
