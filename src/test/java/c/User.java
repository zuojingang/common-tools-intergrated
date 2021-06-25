package c;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;

/**
 * @author zuojingang
 * @Title: User
 * @Description: Todo
 * @date 12/31/20 7:00 PM
 */
public class User {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        Map<Integer, Integer> userCount = new HashMap<>();
        for (User user : users) {
            Integer age = user.getAge();
            userCount.putIfAbsent(age, 0);
            userCount.put(age, userCount.get(age) +1);
        }
    }

    static Map<String, Map<String, Long>> CACHE_MAP = new HashMap<>();
    static Map<String, Long> CACHE_KEY = new HashMap<>();
    public static void setEx(String key, String value, Long expiredTime){

        Map<String, Long> valueExpiredTime = new HashMap<>();
        valueExpiredTime.put(value, expiredTime);
        CACHE_MAP.put(key, valueExpiredTime);
        CACHE_KEY.put(key, expiredTime);

        if (CACHE_KEY.size() > 1000){
//
//            CACHE_KEY.entrySet().stream().sorted((entry1, entry2) -> {
//                Objects.isNull(entry1.getValue())
//            })
//            String removeKey = CACHE_KEY.remove(0);
//            CACHE_MAP.remove(removeKey);
        }
    }

    public static String getValue(String key){

        if (!CACHE_KEY.containsKey(key)){
            return null;
        }
        Map<String, Long> valueCache = CACHE_MAP.get(key);
        if (Objects.isNull(valueCache) || valueCache.isEmpty()){
            return null;
        }
        Map.Entry<String, Long> valueExpiredEntry = valueCache.entrySet().iterator().next();
        String value = valueExpiredEntry.getKey();
        Long expiredTime = valueExpiredEntry.getValue();
        if (Objects.isNull(expiredTime) || expiredTime > System.currentTimeMillis()){
            return value;
        }
        return null;
    }
}
