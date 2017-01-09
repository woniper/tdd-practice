package net.woniper.tdd.toby.chapter7.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class UppercaseHandler implements InvocationHandler {

    Object target;

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args);
        if(ret instanceof String) {
            return ((String)ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
