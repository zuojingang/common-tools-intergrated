package c;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuojingang
 * @Title: TestWangFanXin
 * @Description: Todo
 * @date 3/17/21 8:14 PM
 */
public class TestWangFanXin {

    public static void main(String[] args) {

//        for (int i=0;i<7;i++){
//
//            try {
//                System.out.println(sumNLine(i));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        Arrays.asList("q", "2", 6);
        
        String jsonStr = "[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,0]";
        List<Long> originList = JSON.parseArray(jsonStr, Long.class);
        System.out.println("originList="+originList);

        List<List<Long>> originListCollection = new ArrayList<>();
        int longMaxOffset = 62;
        for (int i = 0; i < originList.size(); i+=longMaxOffset-1) {
            List<Long> list = new ArrayList<>(longMaxOffset);
            list = originList.subList(i, Math.min(i+longMaxOffset-1, originList.size()));
            originListCollection.add(list);
        }
//        System.out.println("xxxginList="+originListCollection);

        List<Long> collect = originListCollection.stream()
                .map(x -> {

                    long temp = 1L << x.size();
                    for (int i = 0; i < x.size(); i++) {
                        Long positionVal = x.get(i);
                        temp |= positionVal << i;
                    }
                    return temp;
                }).collect(Collectors.toList());
        System.out.println("collect=" + collect);

        System.out.println("逆向开始==========================");

        List<Long> ariginList = collect.stream()
                .flatMap(x -> {

                    List<Long> longList = new ArrayList<>();
                    while (x > 1) {
                        long temp = x >> 1;
                        long positionVal = x - (temp << 1);
                        longList.add(positionVal);
                        x = temp;
                    }
                    return longList.stream();
                }).collect(Collectors.toList());
        System.out.println("ariginList="+ariginList);

        System.out.println("ariginList.equals(originList)="+ariginList.equals(originList));
    }

    public static int sumNLine(int n){
        if (1>n){
            throw new IllegalArgumentException("illegal argument.");
        }
       if (1==n){
           return 1;
       }
        int preLine = n - 1;
        return 2*sumNLine(preLine)-preLine+2*n;
    }
}
