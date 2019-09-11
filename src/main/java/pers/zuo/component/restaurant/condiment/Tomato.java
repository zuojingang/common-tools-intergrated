package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.Meal.Meal;

/**
 * @author zuojingang
 * @Title: Tomato
 * @Description: 西红柿
 * @date 2019-09-11 17:13
 */
public class Tomato extends CondimentDecorator {

    public Tomato(Meal meal) {
        super(meal);
    }

    @Override
    public String getDescription() {
        return "西红柿" + meal.getDescription();
    }

    @Override
    public double cost() {
        return meal.cost() + 2.0;
    }
}
