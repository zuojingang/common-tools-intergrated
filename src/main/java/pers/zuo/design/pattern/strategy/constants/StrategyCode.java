package pers.zuo.design.pattern.strategy.constants;

/**
 * @author zuojingang
 * @Title: StrategyCode
 * @Description: 策略Code
 * @date 2020/3/16 17:23
 */
public interface StrategyCode {
    /**
     * 定义一个默认策略code，用于所有指挥器
     */
    int DEFAULT = 0;

    /**
     * 示例，具体业务定义具体的策略code，可以定义在自己项目的常量类中
     */
    interface Example {

        int SPECIFIC = 1;
    }
}
