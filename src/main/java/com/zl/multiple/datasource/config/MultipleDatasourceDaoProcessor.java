package com.zl.multiple.datasource.config;

import com.zl.multiple.datasource.entity.SelectDatabase;
import com.zl.multiple.datasource.util.DatabaseContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

public class MultipleDatasourceDaoProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        SelectDatabase selected = AnnotationUtils.findAnnotation(targetClass, SelectDatabase.class);
        if (selected == null) {
            for (Method method : targetClass.getDeclaredMethods()) {
                selected = AnnotationUtils.findAnnotation(method, SelectDatabase.class);
                if (selected != null) {
                    break;
                }
            }
        }

        //如果没有注解返回真实对象
        if (selected == null) {
            return bean;
        }
        //如果有注解，创建代理类
        return Enhancer.create(bean.getClass(), new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object invoke;
                SelectDatabase selectDatabase = method.getAnnotation(SelectDatabase.class);

                if (selectDatabase == null) {
                    selectDatabase = method.getDeclaringClass().getAnnotation(SelectDatabase.class);
                }

                if (selectDatabase != null) {
                    DatabaseContextHolder.setDatabaseType(selectDatabase.value());
                    invoke = methodProxy.invokeSuper(method, objects);
                    DatabaseContextHolder.setDatabaseType(SelectDatabase.DEFAULT_DATABASE);
                } else {
                    invoke = methodProxy.invokeSuper(method, objects);
                }
                return invoke;
            }
        });
    }
}
