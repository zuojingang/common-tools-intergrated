package pers.zuo.design.pattern.template.chain;

import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.chain.node.IChainNode;
import pers.zuo.design.pattern.template.constants.enums.Position;

/**
 * @author zuojingang
 * @Title: IChain
 * @Description: 责任链接口定义
 * @date 2019-12-16 16:34
 */
public interface IChain<P> {

    /**
     * 设置应用上下文
     */
    IChain<P> withApplicationContext(ApplicationContext applicationContext);

    IChain<P> addNode(IChainNode<P> node);

    IChain<P> subChain(Position position);

    void exec(P params);
}
