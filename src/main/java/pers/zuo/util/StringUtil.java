package pers.zuo.util;

import java.util.List;

public class StringUtil {

	public static boolean isEmpty(String target) {
		return null == target || target.isEmpty();
	}

	public static boolean isNotEmpty(String target) {
		return !isEmpty(target);
	}

	public static boolean isBlank(String target) {
		return isEmpty(target) || isEmpty(target.trim());
	}

	public static boolean isNotBlank(String target) {
		return !isBlank(target);
	}

	public static String join(List<? extends Object> list, String separator) {
		if (null == list || list.isEmpty()) {
			return null;
		}
		StringBuilder retStr = new StringBuilder();
		for (Object object : list) {
			String objStr = null == object ? null : object.toString();
			retStr.append(objStr);
			if (null != separator) {
				retStr.append(separator);
			}
		}
		if (null != separator && retStr.length() > 0) {
			return retStr.substring(0, retStr.length() - 1);
		}
		return null;
	}
}
