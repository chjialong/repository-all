package com.jialong.repository.mybatis.reflection.factory;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class SetterFilter implements CallbackFilter {
    static SetterFilter INSTANCE;

    static {
        INSTANCE = new SetterFilter();
    }

    public int accept(Method method) {
        String methodName = method.getName();
        if (methodName.length() <= 3 || !PropertyNamer.isSetter(methodName)) {
            return 0;
        }
        if (method.getParameterCount() != 1) {
            //不是合理setXXX方法
            return 0;
        }
        return 1;
    }
}
