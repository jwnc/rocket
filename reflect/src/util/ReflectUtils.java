package util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public class ReflectUtils {
    public static void checkType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            if (actualType instanceof TypeVariable) {// 泛型类型，比如T
                TypeVariable typeVariable = (TypeVariable) actualType;
                System.out.println("TypeVariable类型: " + typeVariable);

            } else if (actualType instanceof WildcardType) {// 含通配符? 类型
                WildcardType wildcardType = (WildcardType) actualType;
                System.out.println("WildcardType类型: " + wildcardType);

            } else if (actualType instanceof Class) { // 普通类对象
                Class cls = (Class) actualType;
                System.out.println("Class类型: " + actualType); // class
            }
        }
    }

    public static Field findFieldRecurse(Class clazz, String fieldName) {
        Field declaredField = null;
        try {
            declaredField = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
        }
        if (declaredField == null && clazz.getSuperclass() != null) {
            return findFieldRecurse(clazz.getSuperclass(), fieldName);
        }

        return declaredField;
    }
}
