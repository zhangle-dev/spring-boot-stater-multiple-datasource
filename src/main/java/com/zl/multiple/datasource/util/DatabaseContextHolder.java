package com.zl.multiple.datasource.util;

import java.lang.annotation.Annotation;

/**
 * 作用：
 * 1、保存一个线程安全的DatabaseType容器
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    public static String getDatabaseType(){
        return contextHolder.get();
    }

    public static void setDatabaseType(String type) {
        contextHolder.set(type);
    }
}