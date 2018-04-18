package pers.zuo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final ThreadLocal<SimpleDateFormat> ymdhmsDash = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};

	public static Date toDate(String toParse, SimpleDateFormat dateFormat) {
		try {
			return dateFormat.parse(toParse);
		} catch (ParseException e) {
			return null;
		}
	}
}
