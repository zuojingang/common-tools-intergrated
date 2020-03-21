package pers.zuo.design.pattern.strategy;

import org.springframework.context.ApplicationContext;

/**
 * @author zuojingang
 * @Title: IStrategy
 * @Description: 策略接口
 * @date 2020/3/16 15:55
 */
public interface IStrategy<I, O> {

    IStrategy<I, O> withApplicationContext(ApplicationContext applicationContext);

    O process(I params);
}
