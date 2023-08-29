package pers.zuo.design.pattern.template;

import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.chain.IChain;
import pers.zuo.design.pattern.template.chain.PositionChain;

/**
 * @author zuojingang
 * @Title: ITemplate
 * @Description: 执行模版接口
 * @date 2019-12-16 17:36
 */
public interface ITemplate<P, O> {

    /**
     * 设置应用上下文
     *
     * @param applicationContext
     * @return
     */
    ITemplate<P, O> withApplicationContext(ApplicationContext applicationContext);

    /**
     * 设置责任链
     *
     * @param chain
     * @return
     */
    ITemplate<P, O> withChain(IChain<P> chain);

    /**
     * 初始化责任链
     */
    ITemplate<P, O> initChain();

    /**
     * 模版整合责任链的具体实现
     *
     * @param params
     * @return
     */
    O process(P params);

    /**
     * 模版层次的逻辑
     *
     * @param params
     * @return
     */
    O templateProcess(P params);
}
