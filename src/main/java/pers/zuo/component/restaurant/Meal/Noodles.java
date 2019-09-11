package pers.zuo.component.restaurant.Meal;

/**
 * @author zuojingang
 * @Title: Noodles
 * @Description: 炒面
 * @date 2019-09-11 17:09
 */
public class Noodles implements Meal {

    @Override
    public String getDescription() {
        return "炒面";
    }

    @Override
    public double cost() {
        return 8.0;
    }
}
