package pers.zuo.component.restaurant;

import pers.zuo.component.restaurant.meal.IMeal;
import pers.zuo.component.restaurant.meal.Noodles;
import pers.zuo.component.restaurant.meal.Powder;
import pers.zuo.component.restaurant.condiment.AbstractCondimentDecorator;
import pers.zuo.component.restaurant.condiment.Egg;
import pers.zuo.component.restaurant.condiment.Meat;
import pers.zuo.component.restaurant.condiment.Tomato;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zuojingang
 * @Title: Kitchen
 * @Description: 厨房/调制器
 * @date 2019-09-11 17:34
 */
public class Kitchen {

    public static IMeal cooking(@NotNull Class<? extends IMeal> meal, Class<? extends AbstractCondimentDecorator>... condiments){

        IMeal IMealInstance = null;
        try {
            IMealInstance = meal.newInstance();
        } catch (InstantiationException |IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        for (Class<? extends AbstractCondimentDecorator> decorator: condiments){
            try {
                IMealInstance = decorator.getConstructor(IMeal.class).newInstance(IMealInstance);
            } catch (InstantiationException | IllegalAccessException |InvocationTargetException |NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return IMealInstance;
    }

    public static void main(String[] args){

        /**
         * 支个摊子
         */
        IMeal IMeal = cooking(Noodles.class, Meat.class);

        System.out.println(composeMenuItem(IMeal.getDescription(), IMeal.cost()));

        IMeal IMeal1 = cooking(Noodles.class, Egg.class, Tomato.class);

        System.out.println(composeMenuItem(IMeal1.getDescription(), IMeal1.cost()));

        IMeal IMeal2 = cooking(Powder.class, Meat.class);

        System.out.println(composeMenuItem(IMeal2.getDescription(), IMeal2.cost()));

        IMeal IMeal3 = cooking(Powder.class, Egg.class, Tomato.class);

        System.out.println(composeMenuItem(IMeal3.getDescription(), IMeal3.cost()));
    }

    public static String composeMenuItem(String description, double cost){
        return description + " " + cost + "元";
    }
}
