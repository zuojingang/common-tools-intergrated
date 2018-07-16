package pers.zuo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static <E> boolean isEmpty(Collection<E> collection) {
		return null == collection || collection.isEmpty();
	}

	public static <E> boolean isNotEmpty(Collection<E> collection) {
		return !isEmpty(collection);
	}

	@SuppressWarnings("unchecked")
	public static <P, E> List<P> fieldList(List<E> eList, String pName) {
		if (isEmpty(eList) || StringUtil.isBlank(pName)) {
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
