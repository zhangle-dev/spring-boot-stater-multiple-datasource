package com.zl.multiple.datasource.config;

import com.zl.multiple.datasource.entity.SelectDatabase;
import com.zl.multiple.datasource.util.DatabaseContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAspect {

    public DataSourceAspect() {
        System.out.println("Aspect init");
    }

    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("@annotation(com.zl.multiple.datasource.entity.SelectDatabase)")
    public void pointcut() {}

    @Before("pointcut()")
    public void setDataSourceKey(JoinPoint point){

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        //根据连接点所属的类实例，动态切换数据源
        SelectDatabase selected = AnnotationUtils.findAnnotation(method, SelectDatabase.class);

        if (selected == null) {
            DatabaseContextHolder.setDatabaseType(SelectDatabase.DEFAULT_DATABASE);
        } else {
            DatabaseContextHolder.setDatabaseType(selected.value());
        }
    }
}