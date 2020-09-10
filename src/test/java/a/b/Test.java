package a.b;

import com.alibaba.fastjson.JSON;

import java.util.Objects;

/**
 * @author zuojingang
 * @Title: Test
 * @Description: Todo
 * @date 2019-11-01 15:47
 */
public class Test {

    public static void main(String[] args) {

        final int sd= 1;
        Integer dddd= 1;
        boolean equals = Objects.equals(sd, dddd);

        System.out.println(equals);

        Object parse = JSON.parse("123");

        System.out.println(parse);
    }

}
