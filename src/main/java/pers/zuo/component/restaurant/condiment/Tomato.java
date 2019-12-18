package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.meal.IMeal;

/**
 * @author zuojingang
 * @Title: Tomato
 * @Description: 西红柿
 * @date 2019-09-11 17:13
 */
public class Tomato extends AbstractCondimentDecorator {

    public Tomato(IMeal IMeal) {
        super(IMeal);
    }

    @Override
    public String getDescription() {
        return "西红柿" + IMeal.getDescription();
    }

    @Override
    public double cost() {
        return IMeal.cost() + 2.0;
    }
}
