package pers.zuo.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import pers.zuo.lambda.TransformActuator;

import java.util.*;

/**
 * @author zuojingang
 * @Title: CollectionUtil
 * @Description: Todo
 * @date 2019-09-18 12:03
 */
public class ListUtil {

    /**
     * 定义一个给定长度的List，填充null
     * @param size
     * @param <T>
     * @return
     */
    public static<T> List<T> nullList(int size){
        List<T> list = new ArrayList<>(size);
        for(int i=0;i< size; i++){
            list.add(null);
        }
        return list;
    }

    /**
     * 判断给定List是否为空，并获取给定List的第一个值
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T first(List<T> list){
        return org.springframework.util.CollectionUtils.isEmpty(list)? null: list.get(0);
    }

    /**
     * 提取给定List的元素item的名为pName的属性值，并组装成List返回键
     *
     * @param eList
     * @param pName
     * @param <P>
     * @param <E>
     * @return
     */
    public static <P, E> List<P> fieldList(List<E> eList, String pName) {
        return fieldList(eList, pName, null);
    }

    /**
     * 提取给定List的元素item的名为pName的属性值，并进行类型转换组装成List返回键
     *
     * @param eList    对象列表
     * @param pName    要提取的属性名称
     * @param actuator 属性值的转换器
     * @param <P>      最终返回的列表元素类型
     * @param <E>      要进行提取的列表元素类型
     * @return
     */
    public static <P, E> List<P> fieldList(List<E> eList, String pName, TransformActuator actuator) {
        if (CollectionUtils.isEmpty(eList) || StringUtils.isBlank(pName)) {
            return Collections.emptyList();
        }
        List<P> pList = new ArrayList<P>();
        try {
            String methodName = new StringBuffer("get").append(pName.substring(0, 1).toUpperCase())
                    .append(pName.substring(1)).toString();
            for (E e : eList) {
                Object fieldVal = e.getClass().getMethod(methodName).invoke(e);
                if (Objects.isNull(actuator)) {
                    pList.add((P) fieldVal);
                    continue;
                }
                pList.add((P) actuator.execute(fieldVal));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pList;
    }

    /**
     * 提取给定List的元素item的名为keyProp的属性值val，并以val为key，以其所对应的item为value，组装Map并返回
     * @param inputList
     * @param keyProp
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> list2Map(List<V> inputList, String keyProp) {
        Map<K, V> result = new HashMap<>();
        try {
            if (inputList != null) {
                String methodName = "get" + keyProp.substring(0, 1).toUpperCase() + keyProp.substring(1);
                for (V obj : inputList) {
                    result.put((K) obj.getClass().getMethod(methodName).invoke(obj), obj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 提取给定List的元素item的名为keyProp的属性值val，并以val为key，以其所对应的item列表为value，组装Map并返回
     * @param inputList
     * @param keyProp
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, List<V>> list2MapList(List<V> inputList, String keyProp) {
        Map<K, List<V>> result = new HashMap<>();
        try {
            if (inputList != null) {
                String methodName = "get" + keyProp.substring(0, 1).toUpperCase() + keyProp.substring(1);
                for (V obj : inputList) {
                    K key = (K) obj.getClass().getMethod(methodName).invoke(obj);
                    result.putIfAbsent(key, new ArrayList<>());
                    result.get(key).add(obj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
