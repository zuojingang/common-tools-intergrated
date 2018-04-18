package pers.zuo.util;

public class StringUtils {

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
}
