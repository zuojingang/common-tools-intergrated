package pers.zuo.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * 获取给定类targetClass的实例
     * @param targetClass
     * @param <T>
     * @return
     */
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
     * Judge the bean fields are all null
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T bean) {
        return isNoneFieldInit(bean, false);
    }

    /**
     * Judge the bean fields are all null (or blank when field type is String)
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean isBlank(T bean) {
        return isNoneFieldInit(bean, true);
    }

    /**
     * Judge the bean fields are all not init.
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean isNoneFieldInit(T bean, boolean blank) {
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
            flag = flag && blank? (Objects.isNull(fieldVal) || StringUtils.isBlank(fieldVal.toString())): Objects.isNull(fieldVal);
        }
        return flag;
    }

    /**
     * 设置给定对象 t 的属性 fieldName 值为 val
     * @param t
     * @param fieldName
     * @param val
     * @param <T>
     * @param <F>
     * @return
     */
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

    /**
     * 获取给定对象 t 的属性 fieldName 的值
     * @param t
     * @param fieldName
     * @param <T>
     * @param <F>
     * @return
     */
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

    /**
     * 获取给定类clazz 的属性 fieldName，并将其设置为可访问
     * @param clazz
     * @param fieldName
     * @param <T>
     * @return
     */
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

    /**
     * 获取给定类clazz 的所有属性，并设置为可访问，返回属性List
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<Field> getAllFieldsThenSetAccessible(Class<T> clazz){
        List<Field> fields = new ArrayList<>();
        getAllFieldsThenSetAccessible(clazz, fields);
        return fields;
    }

    /**
     * 获取给定类clazz 的所有属性，并设置为可访问，填充至fields，无返回值
     * @param clazz
     * @param fields
     * @param <T>
     */
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

    /**
     * 获取给定类clazz 的所有属性名，并返回属性名List
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<String> getAllFieldsName(Class<T> clazz){
        List<String> fieldsName = new ArrayList<>();
        getAllFieldsName(clazz, fieldsName);
        return fieldsName;
    }

    /**
     * 获取给定clazz 的所有属性名，并填充至fieldsName ，无返回值
     * @param clazz
     * @param fieldsName
     * @param <T>
     */
    public static <T> void getAllFieldsName(Class<T> clazz, List<String> fieldsName){
        if (Objects.isNull(clazz)){
            return;
        }
        Field[] fieldArray = clazz.getDeclaredFields();
        if (0 == fieldArray.length){
            getAllFieldsName(clazz.getSuperclass(), fieldsName);
            return;
        }
        for (Field field : fieldArray) {
            fieldsName.add(field.getName());
        }
        getAllFieldsName(clazz.getSuperclass(), fieldsName);
    }

    public static <T> void trimFields(T data) {
        if (Objects.isNull(data)) {
            return;
        }
        trimFields(data, 0, 2);
    }

    public static <T> void trimFields(T data, int targetDepth) {
        if (Objects.isNull(data)) {
            return;
        }
        trimFields(data, 0, targetDepth);
    }

    /**
     * 去除所有String属性两端空格
     * 为防止性能过度消耗，定义目标深度targetDepth
     *
     * @param data
     * @param currentDepth
     * @param targetDepth
     * @param <T>
     */
    public static <T> void trimFields(T data, int currentDepth, int targetDepth) {
        if (Objects.isNull(data) || targetDepth < currentDepth) {
            return;
        }
        List<Field> fieldList = getAllFieldsThenSetAccessible(data.getClass());
        for (Field field : fieldList) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object val = null;
            try {
                val = field.get(data);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (val instanceof List) {
                for (Object item : (List) val) {
                    trimFields(item, currentDepth+1, targetDepth);
                }
            }
            if (val instanceof Object[]) {
                for (Object item : (Object[]) val) {
                    trimFields(item, currentDepth+1, targetDepth);
                }
            }
            if (val instanceof String) {
                setFieldVal(data, field.getName(), val.toString().trim());
            }
        }
    }

    public static <T> Object invokeMethod(T obj, String name){
        Method method = getMethod(obj.getClass(), name);
        if (Objects.isNull(method)){
            return null;
        }
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Method getMethod(Class<T> clazz, String name, Class<?> ...argumentTypes){
        try {
            return clazz.getMethod(name, argumentTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
