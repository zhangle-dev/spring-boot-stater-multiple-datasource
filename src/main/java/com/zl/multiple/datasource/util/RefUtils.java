package com.zl.multiple.datasource.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @program: springboot-multiple-dataSources
 * @description: 反射相关工具类
 * @author: 张乐
 * @create: 2019-01-14 10:42
 **/
public class RefUtils {

    /**
     * 判断方法上是否有指定类型的注解的注解,如果不存在返回null
     * @param method 方法
     * @param annotation 注解
     */
    public static Annotation getMethedAnnotation(Method method, Class<? extends Annotation> annotation) {

        Annotation[] annotations = method.getAnnotations();
        for (Annotation anno : annotations) {
            boolean hasAnnotation = anno.getClass().isAnnotationPresent(annotation);
            if (hasAnnotation) {
                return anno;
            }
        }
        return null;
    }
}
