package c;

import java.util.regex.Pattern;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static java.util.regex.Pattern.compile;

public class Test1 {

	public static void main(String[] args) {

		Pattern pattern = compile("^\\d+(\\.\\d)?$");

		System.out.println(pattern.matcher("3.256").matches());
		System.out.println(pattern.matcher("3.2").matches());
		System.out.println(pattern.matcher("3").matches());
		System.out.println(pattern.matcher("0.3").matches());
		System.out.println(pattern.matcher("0.32").matches());
		System.out.println(pattern.matcher("e").matches());


		IA ia = new IA();
		ia.setId("dfs");
		A a = new A();
		a.setId("fff");
		a.setNumber(4);
		IA iaa = a;

		A aaai = parseObject(toJSONString(ia), A.class);
		A aai = parseObject(toJSONString(iaa), A.class);

		System.out.println(11);
	}

	public static class IA{
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	public static class A extends IA{
		private Integer number;

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}
	}

}
