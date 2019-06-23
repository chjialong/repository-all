package com.jialong.repository.mybatis.reflection.factory;

import org.apache.ibatis.reflection.ReflectionException;

import java.util.Locale;

public class PropertyNamer {
    private PropertyNamer() {
    }

    public static String methodToProperty(String name, boolean firstLetterToLower) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            name = name.substring(3);
        }
        if (!firstLetterToLower) {
            return name;
        }
        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static boolean isProperty(String name) {
        return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
    }

    public static boolean isGetter(String name) {
        return name.startsWith("get") || name.startsWith("is");
    }

    public static boolean isSetter(String name) {
        return name.startsWith("set");
    }
}
