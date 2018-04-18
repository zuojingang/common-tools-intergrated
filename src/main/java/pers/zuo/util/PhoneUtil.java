package pers.zuo.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import pers.zuo.consts.PhoneOperators;

public class PhoneUtil {

	private static final Pattern phonePattern = Pattern
			.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");;

	public static String maskPhone(String phone) {
		if (isValidPhone(phone)) {
			return phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return "";
	}

	public static boolean isValidPhone(String phone) {
		if (StringUtils.isBlank(phone) || !phonePattern.matcher(phone).matches()) {
			return false;
		}
		return true;
	}

	public static int getPhoneOperator(String phone) {
		for (Map.Entry<Integer, List<String>> entry : OperatorPrefixMap.entrySet()) {
			for (String prefix : entry.getValue()) {
				if (phone.startsWith(prefix)) {
					return entry.getKey();
				}
			}
		}
		return PhoneOperators.UNKNOWN;
	}

	private static final Map<Integer, List<String>> OperatorPrefixMap = new HashMap<Integer, List<String>>();

	static {
		OperatorPrefixMap.put(PhoneOperators.CHINA_TELECOM,
				Arrays.asList("133", "153", "177", "180", "181", "189", "1349", "1700"));
		OperatorPrefixMap.put(PhoneOperators.CHINA_MOBILE, Arrays.asList("134", "135", "136", "137", "138", "139",
				"150", "151", "152", "157", "158", "159", "182", "183", "184", "187", "178", "188", "147", "1705"));
		OperatorPrefixMap.put(PhoneOperators.CHINA_UNICOM,
				Arrays.asList("130", "131", "132", "145", "155", "156", "176", "185", "186", "1709"));
	}

	/**
	 * 手机号码正则表达式
	 * "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$"
	 */
	public static String mockMaskPhone() {
		int first = 1;
		int second = RandomUtil.randomInt(3, 10);
		int third = 0;
		int[] temp = null;
		switch (second) {
		case 3:
		case 8:
			third = RandomUtil.randomInt(0, 10);
			break;
		case 4:
			temp = new int[] { 5, 7, 9 };
			third = temp[RandomUtil.randomInt(0, 3)];
			break;
		case 5:
			temp = new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 9 };
			third = temp[RandomUtil.randomInt(0, 9)];
			break;
		case 6:
			third = 6;
			break;
		case 7:
			temp = new int[] { 0, 1, 3, 5, 6, 7, 8 };
			third = temp[RandomUtil.randomInt(0, 7)];
			break;
		case 9:
			third = RandomUtil.randomInt(8, 10);
			break;

		default:
			break;
		}
		Integer lastFour = 0;
		do {
			lastFour = RandomUtil.randomInt(1000, 9999);
			String laString = lastFour.toString();
			for (int i = 0; i < laString.length(); i++) {
				char j = laString.charAt(i);
				String afterReplace = laString.replace(new String(new char[] { j }), "");
				if (afterReplace.trim().length() <= 1) {
					lastFour = 0;
					break;
				}
			}
		} while (0 == lastFour);
		String mockPhone = new StringBuilder().append(first).append(second).append(third).append("****")
				.append(lastFour).toString();
		return mockPhone;
	}

}
