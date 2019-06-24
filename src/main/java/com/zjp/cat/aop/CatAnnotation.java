package com.zjp.cat.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于监控方法级别的信息
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface CatAnnotation {
}
