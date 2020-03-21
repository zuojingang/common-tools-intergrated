package pers.zuo.design.pattern.strategy;

import org.springframework.context.ApplicationContext;

/**
 * @author zuojingang
 * @Title: AbstractStrategy
 * @Description: 策略抽象类
 * @date 2020/3/16 16:02
 */
public abstract class AbstractStrategy<I, O> implements IStrategy<I, O> {

    protected ApplicationContext applicationContext;

    @Override
    public IStrategy<I, O> withApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }
}
