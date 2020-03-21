package pers.zuo.design.pattern.strategy.example;

/**
 * @author zuojingang
 * @Title: SpecificExampleStrategy
 * @Description: 示例，特定处理
 * @date 2020/3/16 16:17
 */
public class SpecificExampleStrategy extends DefaultExampleStrategy {

    @Override
    public Void process(Object params) {
        super.process(params);
        return null;
    }
}
