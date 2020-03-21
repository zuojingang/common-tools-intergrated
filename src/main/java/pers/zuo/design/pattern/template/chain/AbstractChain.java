package pers.zuo.design.pattern.template.chain;


import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.chain.node.IChainNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuojingang
 * @Title: AbstractChain
 * @Description: 责任链抽象类定义
 * @date 2019-12-16 16:34
 */
public abstract class AbstractChain<P> implements IChain<P> {

    protected final List<IChainNode<P>> chainNodes = new ArrayList<>();
    protected ApplicationContext applicationContext;

    @Override
    public IChain<P> withApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    @Override
    public IChain<P> addNode(IChainNode<P> node) {
        node.withApplicationContext(applicationContext);
        chainNodes.add(node);
        return this;
    }

}
