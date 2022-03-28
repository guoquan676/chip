package com.template.chip.component.exception.annotation;

import java.lang.annotation.*;

/**
 * @author gzq
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonResponseBody {
}