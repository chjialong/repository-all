package com.jialong.repository.mybatis.reflection.factory;

import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectWrapper implements MethodInterceptor {

    /**
     * 原始数据对象
     */
    private Object target;

    private boolean changeTracking = false;

    /**
     * target的Class的所有getter
     */
    private Map<String, Method> getterMap = new HashMap<>();

    /**
     * {key: set方法名称, value: }
     */
    private Map<String, PropertyWrapper> propertyWrapperMap = new HashMap<>();

    public Map<String, PropertyWrapper> getPropertyWrapperMap() {
        return propertyWrapperMap;
    }

    public Map<String, Method> getGetterMap() {
        return getterMap;
    }

    public void init(Object target, Class clazz) {
        this.target = target;
        for (Method method : clazz.getDeclaredMethods()) {
            if (!PropertyNamer.isGetter(method.getName())) {
                continue;
            }
            this.getterMap.put(method.getName(), method);
        }
    }

    public void startTracking() {
        this.changeTracking = true;
    }

    public static Map<String, Object> determineChangedFields(Object value) {
        if (!(value instanceof Factory)) {
            return null;
        }
        Factory factory = (Factory) value;
        ObjectWrapper objectWrapper = (ObjectWrapper) factory.getCallback(1);
        Map<String, Object> changedFieldMap = new HashMap<>();
        for (Map.Entry<String, PropertyWrapper> entry : objectWrapper.getPropertyWrapperMap().entrySet()) {
            PropertyWrapper propertyWrapper = entry.getValue();
            if (!propertyWrapper.isChanged()) {
                continue;
            }
            changedFieldMap.put(propertyWrapper.getFieldName(), propertyWrapper.getNewValue());
        }
        return changedFieldMap;
    }

    @Override
    public Object intercept(Object obj, Method setter, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (!this.changeTracking) {
            return methodProxy.invokeSuper(obj, args);
        }
        if (this.target != obj) {
            throw new RuntimeException("跟踪的对象已经被替换，无法继续处理。");
        }
        PropertyWrapper propertyWrapper = this.createOrGetPropertyWrapper(setter);
        if (propertyWrapper != null) {
            this.determineValueChange(obj, propertyWrapper, args[0]);
        }
        return methodProxy.invokeSuper(obj, args);
    }

    private void determineValueChange(Object obj, PropertyWrapper propertyWrapper, Object newValue) throws Throwable {
        Method getter = propertyWrapper.getGetter();
        Object oldValue = getter.invoke(obj, null);
        if (newValue == oldValue) {
            return;
        }
        if (newValue == null || !newValue.equals(oldValue)) {
            propertyWrapper.setNewValue(newValue);
        }
    }

    private PropertyWrapper createOrGetPropertyWrapper(Method setter) {
        String setterName = setter.getName();
        if (this.propertyWrapperMap.containsKey(setterName)) {
            return this.propertyWrapperMap.get(setterName);
        }
        PropertyWrapper propertyWrapper = PropertyWrapper.create(this, setter);
        this.propertyWrapperMap.put(setterName, propertyWrapper);
        return propertyWrapper;
    }
}