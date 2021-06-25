package c;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

public class Test1 {

//	public static void main(String[] args) throws ClassNotFoundException {
//
//		String urlTest_1 = "HTTP://www.baidu.com";
//		String urlTest_2 = "HTTPS://www.baidu.com";
//		String urlTest_1_1 = "HTTP://WWW.BAIDU.COM";
//		String urlTest_2_2 = "HTTPS://WWW.BAIDU.COM";
//
//		for (String urlTest : Arrays.asList(urlTest_1, urlTest_2, urlTest_1_1, urlTest_2_2)) {
//
//			URI uri = URI.create(urlTest);
//			System.out.println("uri.getScheme()"+uri.getScheme());
//			System.out.println("uri.getAuthority()"+uri.getAuthority());
//			System.out.println("uri.getPath()"+uri.getPath());
//			System.out.println("uri.getFragment()"+uri.getFragment());
//		}
//	}

	public static void main(String[] args) {

		List<String> testList = null;
		Optional<String> stringOptional = Optional.ofNullable(testList).orElse(Collections.emptyList())
				.stream()
				.filter(
						str -> Objects.nonNull(str) && str.length() > 1
				)
				.min(Comparator.comparing(String::length));


		String testStr = stringOptional.get();

		System.out.println(JSON.toJSONString(testStr));
	}
}
