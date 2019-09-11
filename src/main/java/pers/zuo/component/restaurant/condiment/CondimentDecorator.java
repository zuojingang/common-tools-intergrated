package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.Meal.Meal;

/**
 * @author zuojingang
 * @Title: Seasoning
 * @Description: 调料/加料的餐
 * @date 2019-09-11 16:34
 */
public abstract class CondimentDecorator implements Meal {

    protected final Meal meal;

    public CondimentDecorator(Meal meal) {
        this.meal = meal;
    }
}
