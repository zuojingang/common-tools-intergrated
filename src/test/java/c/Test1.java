package c;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import pers.zuo.util.RandomUtil;

import javax.validation.constraints.Size;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

//	public static void main(String[] args) {
//
//		List<String> testList = null;
//		Optional<String> stringOptional = Optional.ofNullable(testList).orElse(Collections.emptyList())
//				.stream()
//				.filter(
//						str -> Objects.nonNull(str) && str.length() > 1
//				)
//				.min(Comparator.comparing(String::length));
//
//
//		String testStr = stringOptional.get();
//
//		System.out.println(JSON.toJSONString(testStr));
//	}

//	public static void main(String[] args) {
//
//		// 设置日的 LocalTime
//		LocalTime dayCalcTimeNodeLocal = LocalTime.parse("08:03", DateTimeFormatter.ISO_TIME);
//		// 当前时间
//		LocalDateTime now = LocalDateTime.now();
//
//		System.out.println("now: " + now.atZone(ZoneOffset.systemDefault()).toInstant());
//
//		LocalDateTime plus = now.plus(dayCalcTimeNodeLocal.toSecondOfDay(), ChronoUnit.SECONDS);
//
//		LocalDateTime plus11111 = now.plusSeconds(dayCalcTimeNodeLocal.toSecondOfDay());
//
//		System.out.println("now: " + now.atZone(ZoneOffset.systemDefault()).toInstant());
//		System.out.println("plus: " + plus.atZone(ZoneOffset.systemDefault()).toInstant());
//		System.out.println("plus11111: " + plus11111.atZone(ZoneOffset.systemDefault()).toInstant());
//
//	}
//
//	public static void main(String[] args) {
//
//		LocalDate now = LocalDate.now();
//
//		LocalDate localDate = now.minusMonths(5);
//
//		System.out.println("now is: " + now);
//
//		System.out.println("a month ago is: " + localDate);
//	}
//
//	public static void main(String[] args) {
//		List<Integer> integers = Lists.newArrayList(1, 2, 3, 1, 2, 3, 1, 231234);
//		for (Integer integer : integers) {
//
//			integers.remove((Object) 231234);
//			integers.add(1242341212);
//		}
//	}
public static void main(String[] args) {

//	List<SX> list = new ArrayList<>();

	int i =0;
	while (i++<5){

		SX sx1 = new SX();
		sx1.setSssss(new BigDecimal(123));
		sx1.setSssssAAA(new BigDecimal(234));
//
//		System.out.println("===");
//		System.out.println(System.currentTimeMillis());
//		System.out.println("==gc=");
//		System.gc();
//		System.out.println("==gc=");
//		System.out.println(System.currentTimeMillis());


//		sx1 = new SX();
//		sx1.setSssss(new BigDecimal(123));
//		sx1.setSssssAAA(new BigDecimal(234));
//
//		System.out.println("===");
//		System.out.println(System.currentTimeMillis());
//		System.out.println("==gc=");
//		System.gc();
//		System.out.println("==gc=");

		System.out.println(System.currentTimeMillis());
		System.out.println("==runFinalization=");
		System.runFinalization();
		System.out.println("==runFinalization=");
		System.out.println(System.currentTimeMillis());
	}

//	System.out.println(JSON.toJSONString(sx1));
//	A a = JSON.parseObject(JSON.toJSONString(sx1), A.class);
//	System.out.println(JSON.toJSONString(a));


//	list.add(sx1);
//	List<BigDecimal> collect = list.stream().map(x -> x.sssssAAA).collect(Collectors.toList());
//	System.out.println(JSON.toJSONString(collect));
//	System.out.println(JSON.toJSONString(list));

//	AAAA aaaa = new AAAA();
//	aaaa.setGgg(234);
//	aaaa.setAdg("rtrrrrr");
//
//	List<A> list = Lists.newArrayList();
//	list.add(aaaa);
//
//
//	System.out.println(JSON.toJSONString(list));
//
//	List<A> collect = list.stream().collect(Collectors.toList());
//	collect.get(0).setAdg("3");
//
//	System.out.println(JSON.toJSONString(list));
//
//	System.out.println(JSON.toJSONString(collect));

//	LocalDate parse = LocalDate.parse("2018-10-20");
//	LocalDate parseAAA = LocalDate.parse("2018-11-19");
//
//	System.out.println(parseAAA.toEpochDay()-parse.toEpochDay());
//	System.out.println(parse.plusMonths(1));

//	Map<Integer,String> trMap = new TreeMap<>();
//	trMap.put(100,"ddddd");
//	trMap.put(3,"iii");
//	trMap.put(15, "t");
//
//	Iterator<Integer> iterator = trMap.keySet().iterator();
//	while (iterator.hasNext()){
//
//		Integer next = iterator.next();
//		System.out.println(next + "===" + trMap.get(next));
//		iterator.remove();
//	}
//
//	System.out.println(JSON.toJSONString(trMap));
//
//	List<String> strings = Lists.newArrayList("222", "edsds", "e", "t", "43", "222", "e", "222");
//	Map<String, List<String>> collect = strings.stream().collect(Collectors.groupingBy(x -> x));
//
//	System.out.println(JSON.toJSONString(collect));
//
//	int a=0;
//	int b =1;
//	double x = a/b;


//	System.out.println(LocalDateTime.now().plusYears(1000).atZone(ZoneOffset.systemDefault()).toEpochSecond());

//	SX sx = sx1;
////		sx.setSssss(null);
//
//		System.out.println(JSON.toJSONString(sx));

}


public static class SX{

	private BigDecimal sssss = BigDecimal.ZERO;
	private BigDecimal sssssAAA = BigDecimal.ZERO;

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Call finalize!!!");
		super.finalize();
	}

	public BigDecimal getSssss() {
		return sssss;
	}

	public void setSssss(BigDecimal sssss) {
		this.sssss = sssss;
	}

	public BigDecimal getSssssAAA() {
		return sssssAAA;
	}

	public void setSssssAAA(BigDecimal sssssAAA) {
		this.sssssAAA = sssssAAA;
	}
}

public void tescccc(){
	if (true){
	return;
	}
	System.out.println(123);
}

public static class A{

	private String adg;

	public String getAdg() {
		return adg;
	}

	public void setAdg(String adg) {
		this.adg = adg;
	}
}

public static class AAAA extends A {

	private Integer ggg;

	public Integer getGgg() {
		return ggg;
	}

	public void setGgg(Integer ggg) {
		this.ggg = ggg;
	}
}
}
