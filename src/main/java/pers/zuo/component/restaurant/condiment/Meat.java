package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.Meal.Meal;

/**
 * @author zuojingang
 * @Title: Meat
 * @Description: 肉丝
 * @date 2019-09-11 17:18
 */
public class Meat extends CondimentDecorator {

    public Meat(Meal meal) {
        super(meal);
    }

    @Override
    public String getDescription() {
        return "肉丝" + meal.getDescription();
    }

    @Override
    public double cost() {
        return meal.cost() + 3.5;
    }
}
