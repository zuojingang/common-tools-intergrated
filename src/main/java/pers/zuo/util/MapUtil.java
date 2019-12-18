package pers.zuo.util;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 左金剛
 * @Title: MapUtil
 * @Description: Todo
 * @date 2019/5/16 13:34
 */
public class MapUtil {

    public static <T> Map<String, T> buildNotNull(Object... args) {
        return buildMap(null, false, true, args);
    }

    public static <T> Map<String, T> buildNullable(Object... args) {
        return buildMap(null, false, false, args);
    }

    public static <T> Map<String, T> buildNotNullTreeMap(Object... args) {
        return buildMap(null, true, true, args);
    }

    public static <T> Map<String, T> buildNullableTreeMap(Object... args) {
        return buildMap(null, true, false, args);
    }

    public static <T> Map<String, T> buildNotNullTreeMap(Compare compare, Object... args) {
        return buildMap(compare, true, true, args);
    }

    public static <T> Map<String, T> buildNullableTreeMap(Compare compare, Object... args) {
        return buildMap(compare, true, false, args);
    }

    public static <T> Map<String, T> buildNotNullMap(Compare compare, boolean sort, Object... args) {
        return buildMap(compare, sort, true, args);
    }

    public static <T> Map<String, T> buildNullableMap(Compare compare, boolean sort, Object... args) {
        return buildMap(compare, sort, false, args);
    }

    public static <T> Map<String, T> buildMap(Compare compare, boolean sort, boolean notnull, Object... args) {
        if (0 == args.length || 1 == args.length % 2) {
            throw new RuntimeException("params 必须是参数名称，参数值成对出现");
        }
        Map<String, T> map = sort? (Objects.isNull(compare)? new TreeMap<>(): new TreeMap<>((Comparator<? super String>) compare)): new HashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            T val = (T) args[i + 1];
            if (notnull && Objects.isNull(val)){
                continue;
            }
            map.put(String.valueOf(args[i]).trim(), val);
        }
        return map;
    }

    public static <T> Map<String, Object> beanSelectiveFiled2Map(@NotNull T bean, String... fieldNames){
        Map<String,Object> map = new HashMap<>();
        for (String fieldName: fieldNames){
            Object fieldVal = ReflectUtils.getFieldVal(bean, fieldName);
            map.put(fieldName, fieldVal);
        }
        return map;
    }

    public static <T> Map<String, Object> beanNonNullField2Map(@NotNull T bean){
        return beanField2Map(bean, true);
    }

    public static <T> Map<String, Object> beanAllField2Map(@NotNull T bean){
        return beanField2Map(bean, false);
    }

    public static <T> Map<String,Object> beanField2Map(@NotNull T bean, boolean nonNull){
        Map<String,Object> map = new HashMap<>();
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field: declaredFields){
            String fieldName = field.getName();
            Object fieldVal = ReflectUtils.getFieldVal(bean, fieldName);
            if (nonNull && Objects.isNull(fieldVal)){
                continue;
            }
            map.put(fieldName, fieldVal);
        }
        return map;
    }

    public interface Compare extends Comparable<String>{

    }
}
