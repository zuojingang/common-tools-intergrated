package pers.zuo.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 左金剛
 * @Title: ReflectUtils
 * @Description: Todo
 * @date 2019/5/25 16:16
 */
public class ReflectUtils {

    public static <T> T getInstance(Class<T> targetClass) {
        T bean = null;
        try {
            bean = targetClass.newInstance();
        } catch (InstantiationException| IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * Judge the bean fields are all null (or blank when field type is String)
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T bean) {
        List<Field> fieldList = getAllFieldsThenSetAccessible(bean.getClass());
        boolean flag = true;
        for (Field field: fieldList){
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }
            Object fieldVal = null;
            try {
                fieldVal = field.get(bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            flag = flag && (Objects.isNull(fieldVal) || StringUtils.isBlank(fieldVal.toString()));
        }
        return flag;
    }

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

    public static <T> List<Field> getAllFieldsThenSetAccessible(Class<T> clazz){
        List<Field> fields = new ArrayList<>();
        getAllFieldsThenSetAccessible(clazz, fields);
        return fields;
    }

    public static <T> void getAllFieldsThenSetAccessible(Class<T> clazz, List<Field> fields){

        if (Objects.isNull(clazz)){
            return;
        }
        Field[] fieldArray = clazz.getDeclaredFields();
        if (0 == fieldArray.length){
            return;
        }
        for (Field field : fieldArray) {
            field.setAccessible(true);
            fields.add(field);
        }
        getAllFieldsThenSetAccessible(clazz.getSuperclass(), fields);
    }

    public static <T> List<String> getAllFieldsName(Class<T> clazz){
        List<String> fieldsName = new ArrayList<>();
        getAllFieldsName(clazz, fieldsName);
        return fieldsName;
    }

    public static <T> void getAllFieldsName(Class<T> clazz, List<String> fieldsName){
        if (Objects.isNull(clazz)){
            return;
        }
        Field[] fieldArray = clazz.getDeclaredFields();
        if (0 == fieldArray.length){
            return;
        }
        for (Field field : fieldArray) {
            fieldsName.add(field.getName());
        }
        getAllFieldsName(clazz.getSuperclass(), fieldsName);
    }
}
