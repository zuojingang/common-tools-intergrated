package pers.zuo.design.pattern.strategy.example;


import pers.zuo.design.pattern.strategy.AbstractStrategy;

/**
 * @author zuojingang
 * @Title: DefaultExampleStrategy
 * @Description: 示例，默认处理
 * @date 2020/3/16 16:12
 */
public class DefaultExampleStrategy extends AbstractStrategy<Object, Void> {

    @Override
    public Void process(Object params) {
        /*
            Do nothing.
         */
        return null;
    }
}
