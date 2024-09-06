package util;

import annotation.NullableWarning;

import java.lang.reflect.Field;

public class AnnotationChecker {
    public static void checkNulls(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(NullableWarning.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null) {
                        System.out.println("Variable " + field.getName() + " is null in " + clazz.getSimpleName() + "!");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
