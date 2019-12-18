package pers.zuo.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author 左金剛
 * @Title: ReflectUtils
 * @Description: Todo
 * @date 2019/5/25 16:16
 */
public class ReflectUtils {

    public static <T, F> boolean setFieldVal(T t, String fieldName, F val){

        Field field = getFieldThenSetAccessible(t.getClass(), fieldName);
        if (Objects.isNull(field)){
            return false;
        }
        try {
            field.set(t, val);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, F> F getFieldVal(T t, String fieldName){

        Field field = getFieldThenSetAccessible(t.getClass(), fieldName);
        if (Objects.isNull(field)){
            return null;
        }
        try {
            return (F)field.get(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Field getFieldThenSetAccessible(Class<T> clazz, String fieldName){

        if (Objects.isNull(clazz) || Objects.isNull(fieldName)){
            return null;
        }
        try {
            Field field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            return field;
        } catch (Exception e) {
            return getFieldThenSetAccessible(clazz.getSuperclass(), fieldName);
        }
    }
}
