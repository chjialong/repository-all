package com.jialong.repository.mybatis.reflection.factory;

import java.lang.reflect.Method;
import java.util.Map;

public class PropertyWrapper {
    private Method getter;
    private Object newValue;
    private String fieldName;
    private boolean changed;

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
        this.changed = true;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    public static PropertyWrapper create(ObjectWrapper objectWrapper, Method setter) {
        String setterName = setter.getName();
        Class<?> parameterType = setter.getParameterTypes()[0];
        String propertyName = PropertyNamer.methodToProperty(setterName, false);
        String getterName = (parameterType == Boolean.TYPE ? "is" : "get") + propertyName;
        Map<String, Method> getterMap = objectWrapper.getGetterMap();
        if (!getterMap.containsKey(getterName)) {
            return null;
        }
        PropertyWrapper propertyWrapper = new PropertyWrapper();
        propertyWrapper.setFieldName(PropertyNamer.methodToProperty(setterName, true));
        propertyWrapper.setGetter(getterMap.get(getterName));
        return propertyWrapper;
    }
}
