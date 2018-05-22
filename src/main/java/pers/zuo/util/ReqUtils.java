package pers.zuo.util;

import javax.servlet.http.HttpServletRequest;

public class ReqUtils {

	public static int getParamInt(HttpServletRequest req, String paramName) {
		String parameVal = req.getParameter(paramName);
		return Integer.parseInt(null == parameVal ? "0" : parameVal);
	}
}
