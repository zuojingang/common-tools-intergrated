package pers.zuo.component.restaurant.condiment;

import pers.zuo.component.restaurant.meal.IMeal;

/**
 * @author zuojingang
 * @Title: Seasoning
 * @Description: 调料/加料的餐
 * @date 2019-09-11 16:34
 */
public abstract class AbstractCondimentDecorator implements IMeal {

    protected final IMeal IMeal;

    public AbstractCondimentDecorator(IMeal IMeal) {
        this.IMeal = IMeal;
    }
}
