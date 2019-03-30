package com.zl.multiple.datasource.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 选择数据源，必须有参数表示选择哪个数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SelectDatabase {

    String DEFAULT_DATABASE = "default";

    String value();
}
