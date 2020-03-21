package pers.zuo.design.pattern.template;

import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.chain.IChain;
import pers.zuo.design.pattern.template.chain.PositionChain;
import pers.zuo.design.pattern.template.constants.enums.Position;

import java.util.Objects;

/**
 * @author zuojingang
 * @Title: AbstractTemplate
 * @Description: 抽象执行模版
 * @date 2020/3/17 17:29
 */
public abstract class AbstractTemplate<P, O> implements ITemplate<P, O> {

    protected ApplicationContext applicationContext;

    @Override
    public ITemplate<P, O> withApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    @Override
    public O process(P params) {
        PositionChain<P> chain = initChain();
        /*
            如果责任链为空，直接返回
         */
        if (Objects.isNull(chain)) {
            return null;
        }
        /*
            如果前置责任链不为空，执行前置责任链
         */
        IChain<P> beforeChain = chain.subChain(Position.BEFORE);
        if (Objects.nonNull(beforeChain)) {
            beforeChain.exec(params);
        }
        /*
            在前置责任链执行之后，后置责任链执行之前，可能需要进行某些逻辑处理，这部分处理因为不需要任何执行条件，是该模版必然要执行的逻辑，所以不适合提取成动作节点
         */
        O result = templateProcess(params);
        /*
            如果后置责任链不为空，则执行后置责任链
         */
        IChain<P> afterChain = chain.subChain(Position.AFTER);
        if (Objects.nonNull(afterChain)) {
            afterChain.exec(params);
        }
        return result;
    }

}
