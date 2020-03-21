package pers.zuo.design.pattern.restaurant.condiment;

/**
 * @author zuojingang
 * @Title: Meat
 * @Description: 肉丝
 * @date 2019-09-11 17:18
 */
public class Meat extends AbstractCondimentDecorator {

    public Meat(pers.zuo.design.pattern.restaurant.meal.IMeal IMeal) {
        super(IMeal);
    }

    @Override
    public String getDescription() {
        return "肉丝" + IMeal.getDescription();
    }

    @Override
    public double cost() {
        return IMeal.cost() + 3.5;
    }
}
