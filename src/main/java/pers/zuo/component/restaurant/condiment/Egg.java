package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.meal.IMeal;

/**
 * @author zuojingang
 * @Title: Egg
 * @Description: 鸡蛋
 * @date 2019-09-11 17:01
 */
public class Egg extends AbstractCondimentDecorator {

    public Egg(IMeal IMeal) {
        super(IMeal);
    }

    @Override
    public String getDescription() {
        return "鸡蛋" + IMeal.getDescription();
    }

    @Override
    public double cost() {
        return IMeal.cost() + 1.0;
    }
}
