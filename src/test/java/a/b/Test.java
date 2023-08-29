package a.b;


import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zuojingang
 * @Title: Test
 * @Description: Todo
 * @date 2019-11-01 15:47
 */
public class Test {

    public static void main(String[] args) {

//        MethodTest methodTest= param -> {
//            System.out.println(param);
//            return Objects.isNull(param) || param>100;
//        };
//        String s = JSON.toJSONString(methodTest);
//        MethodTest parseObject = JSON.parseObject(s, MethodTest.class);
//        boolean b = parseObject.testMethod(1);
//        System.out.println(b);

        LocalDateTime now = LocalDateTime.now();
        String s1 = JSON.toJSONString(now);
        System.out.println(s1);
        LocalDateTime localDateTime = JSON.parseObject(s1, LocalDateTime.class);
        System.out.println(localDateTime);
//        Method method = new Method();
    }

    interface MethodTest{

        boolean testMethod(Integer param);
    }


}
