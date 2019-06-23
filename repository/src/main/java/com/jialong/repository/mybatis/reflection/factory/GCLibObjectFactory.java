package com.jialong.repository.mybatis.reflection.factory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.util.*;

public class GCLibObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        if (this.checkType(type)) {
            return (T) this.instantiateClass(type, constructorArgTypes, constructorArgs);
        }
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    protected boolean checkType(Class<?> type) {
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            return false;
        }
        if (type == Map.class) {
            return false;
        }
        if (type == SortedSet.class) {
            return false;
        }
        if (type == Set.class) {
            return false;
        }
        if (SimpleTypeRegistry.isSimpleType(type)) {
            return false;
        }
        return true;
    }

    protected <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        try {
            return this.createProxy(type, constructorArgTypes, constructorArgs);
        } catch (Exception var9) {
            StringBuilder argTypes = new StringBuilder();
            if (constructorArgTypes != null && !constructorArgTypes.isEmpty()) {
                Iterator var6 = constructorArgTypes.iterator();
                while (var6.hasNext()) {
                    Class<?> argType = (Class) var6.next();
                    argTypes.append(argType.getSimpleName());
                    argTypes.append(",");
                }
                argTypes.deleteCharAt(argTypes.length() - 1);
            }
            StringBuilder argValues = new StringBuilder();
            if (constructorArgs != null && !constructorArgs.isEmpty()) {
                Iterator var11 = constructorArgs.iterator();
                while (var11.hasNext()) {
                    Object argValue = var11.next();
                    argValues.append(String.valueOf(argValue));
                    argValues.append(",");
                }
                argValues.deleteCharAt(argValues.length() - 1);
            }
            throw new RuntimeException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + var9, var9);
        }
    }

    protected <T> T createProxy(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        ObjectWrapper objectWrapper = new ObjectWrapper();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        //enhancer.setCallbackTypes(new Class[]{NoOp.class, ObjectWrapper.class});
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, objectWrapper});
        enhancer.setCallbackFilter(SetterFilter.INSTANCE);
        T t = (T) enhancer.create();
        objectWrapper.init(t, type);
        return t;
    }
}
