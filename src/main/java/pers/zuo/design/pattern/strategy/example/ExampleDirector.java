package pers.zuo.design.pattern.strategy.example;


import pers.zuo.design.pattern.strategy.constants.StrategyCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zuojingang
 * @Title: ExampleDirector
 * @Description: 示例，指挥器
 * @date 2020/3/16 16:19
 */
public class ExampleDirector {

    private static final Map<Integer, DefaultExampleStrategy> STRATEGY_MAP = new HashMap<>();

    /**
     * 注册策略 之所以定义一个方法，是考虑到在业务逻辑中手动注册策略的可能
     *
     * @param code
     * @param strategy
     * @return
     */
    public static DefaultExampleStrategy register(int code, DefaultExampleStrategy strategy) {
        return STRATEGY_MAP.put(code, strategy);
    }

    /**
     * 根据标识code，获取对应的策略
     *
     * @param code
     * @return
     */
    public static DefaultExampleStrategy get(int code) {
        return STRATEGY_MAP.get(code);
    }

    static {
        register(StrategyCode.DEFAULT, new DefaultExampleStrategy());
        register(StrategyCode.Example.SPECIFIC, new SpecificExampleStrategy());
    }
}
