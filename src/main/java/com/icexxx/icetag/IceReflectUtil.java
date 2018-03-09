package com.icexxx.icetag;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IceReflectUtil {
    public static Object invoke(Object obj, String methodName) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj);
    }

    public static String invokeString(Object obj, String methodName) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        Object value = method.invoke(obj);
        if (value == null) {
            return "";
        } else {
            return value + "";
        }
    }

    public static Object invoke(Object obj, String methodName, String keyName, Object param)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName, String.class, Object.class);
        method.setAccessible(true);
        return method.invoke(obj, keyName, param);
    }

    public static Field getField(Class clazz, String fieldName) {
        if (!"java.lang.Object".equals(clazz.getName())) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                return declaredField;
            } catch (NoSuchFieldException e) {
                return getField(clazz.getSuperclass(), fieldName);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
