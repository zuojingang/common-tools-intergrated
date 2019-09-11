package pers.zuo.component.restaurant;

import com.sun.istack.internal.NotNull;
import pers.zuo.component.restaurant.Meal.Meal;
import pers.zuo.component.restaurant.Meal.Noodles;
import pers.zuo.component.restaurant.Meal.Powder;
import pers.zuo.component.restaurant.condiment.CondimentDecorator;
import pers.zuo.component.restaurant.condiment.Egg;
import pers.zuo.component.restaurant.condiment.Meat;
import pers.zuo.component.restaurant.condiment.Tomato;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zuojingang
 * @Title: Kitchen
 * @Description: 厨房/调制器
 * @date 2019-09-11 17:34
 */
public class Kitchen {

    public static Meal cooking(@NotNull Class<? extends Meal> meal, Class<? extends CondimentDecorator>... condiments){

        Meal mealInstance = null;
        try {
            mealInstance = meal.newInstance();
        } catch (InstantiationException |IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        for (Class<? extends CondimentDecorator> decorator: condiments){
            try {
                mealInstance = decorator.getConstructor(Meal.class).newInstance(mealInstance);
            } catch (InstantiationException | IllegalAccessException |InvocationTargetException |NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return mealInstance;
    }

    public static void main(String[] args){

        /**
         * 支个摊子
         */
        Meal meal = cooking(Noodles.class, Meat.class);

        System.out.println(composeMenuItem(meal.getDescription(), meal.cost()));

        Meal meal1 = cooking(Noodles.class, Egg.class, Tomato.class);

        System.out.println(composeMenuItem(meal1.getDescription(), meal1.cost()));

        Meal meal2 = cooking(Powder.class, Meat.class);

        System.out.println(composeMenuItem(meal2.getDescription(), meal2.cost()));

        Meal meal3 = cooking(Powder.class, Egg.class, Tomato.class);

        System.out.println(composeMenuItem(meal3.getDescription(), meal3.cost()));
    }

    public static String composeMenuItem(String description, double cost){
        return description + " " + cost + "元";
    }
}
