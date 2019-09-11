package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.Meal.Meal;

/**
 * @author zuojingang
 * @Title: Egg
 * @Description: 鸡蛋
 * @date 2019-09-11 17:01
 */
public class Egg extends CondimentDecorator {

    public Egg(Meal meal) {
        super(meal);
    }

    @Override
    public String getDescription() {
        return "鸡蛋" + meal.getDescription();
    }

    @Override
    public double cost() {
        return meal.cost() + 1.0;
    }
}
