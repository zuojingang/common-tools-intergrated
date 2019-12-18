package pers.zuo.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zuojingang
 * @Title: CollectionUtil
 * @Description: Todo
 * @date 2019-09-18 12:03
 */
public class ListUtil {

    public static <T> T first(List<T> list){
        return CollectionUtils.isEmpty(list)? null: list.get(0);
    }

    public static <P, E> List<P> fieldList(List<E> eList, String pName) {
        if (CollectionUtils.isEmpty(eList) || StringUtils.isBlank(pName)) {
            return Collections.emptyList();
        }
        List<P> pList = new ArrayList<P>();
        try {
            String methodName = new StringBuffer("get").append(pName.substring(0, 1).toUpperCase())
                    .append(pName.substring(1)).toString();
            for (E e : eList) {
                pList.add((P) e.getClass().getMethod(methodName).invoke(e));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pList;
    }

    @SuppressWarnings("unchecked")
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
}
