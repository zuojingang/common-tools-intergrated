package pers.zuo.design.pattern.template.chain;


import pers.zuo.design.pattern.template.constants.enums.Position;
import pers.zuo.design.pattern.template.chain.node.IChainNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zuojingang
 * @Title: SimpleChain
 * @Description: 简单的责任链类
 * @date 2019-12-16 17:31
 */
public class SimpleChain<P> extends AbstractChain<P> {

    private final List<IChainNode<P>> chainNodes = new ArrayList<>();

    public SimpleChain() {
    }

    public SimpleChain(List<IChainNode<P>> chainNodes) {
        this.chainNodes.addAll(chainNodes);
    }

    /**
     * 这里的subChain方法不允许调用
     *
     * @param position
     * @return
     */
    @Override
    public IChain<P> subChain(Position position) {
        throw new RuntimeException("SimpleChain can't do subChain!");
    }

    @Override
    public void exec(P params) {
        if (chainNodes.isEmpty()) {
            return;
        }
        /*
          在执行责任链之前，先进行节点排序
          Comparator.comparingInt 默认从小到大
         */
        chainNodes.sort(Comparator.comparingInt(IChainNode::getOrder));
        int idx = 0;
        do {
            IChainNode<P> node = chainNodes.get(idx++);
            /*
              如果当前节点执行条件不满足，或者当前这一步执行成功
              继续执行下一步
             */
            if (!node.check(params) || node.exec(params)) {
                continue;
            }
            return;
        } while (idx < chainNodes.size());
    }
}
