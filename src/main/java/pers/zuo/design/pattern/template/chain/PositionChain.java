package pers.zuo.design.pattern.template.chain;


import pers.zuo.design.pattern.template.chain.node.IChainNode;
import pers.zuo.design.pattern.template.constants.enums.Position;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuojingang
 * @Title: PositionChain
 * @Description: 带有执行位置的责任链类
 * @date 2019-12-16 17:31
 */
public class PositionChain<P> extends AbstractChain<P> {

    @Override
    public IChain<P> subChain(Position position) {
        List<IChainNode<P>> positionNodes = chainNodes.stream().filter(node -> position.check(node.getPosition().code)).collect(Collectors.toList());
        if (positionNodes.isEmpty()) {
            return null;
        }
        SimpleChain<P> simpleChain = new SimpleChain<>(positionNodes);
        simpleChain.withApplicationContext(applicationContext);
        return simpleChain;
    }

    /**
     * 这里的exec方法不允许调用
     *
     * @param params
     */
    @Override
    public void exec(P params) {
        throw new RuntimeException("PositionChain can't do exec!");
    }
}
