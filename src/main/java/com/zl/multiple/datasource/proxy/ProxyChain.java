package com.zl.multiple.datasource.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理对象链，用于存储多个代理对象，避免CGLib嵌套代理的问题
 */
public class ProxyChain<T> implements MethodInterceptor {

    private T target;
    private List<T> proxys;

    public ProxyChain(List<T> proxys, T target) {
        this.target = target;
        this.proxys = proxys;
    }

    public Object intercept(Object o, Method method, final Object[] objects, MethodProxy methodProxy) throws Throwable {
        //调用代理对象的方法
        for (T proxy : this.proxys) {
            method.invoke(proxy, objects);
        }
        //调用原对象的方法
        return method.invoke(target, objects);
    }
}
