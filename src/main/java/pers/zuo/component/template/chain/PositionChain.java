package pers.zuo.component.template.chain;

import org.apache.commons.collections4.CollectionUtils;
import pers.zuo.component.template.chain.node.ChainNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuojingang
 * @Title: PositionChain
 * @Description: 带有执行位置的责任链类
 * @date 2019-12-16 17:31
 */
public class PositionChain extends Chain {

    @Override
    public Chain subChain(Position position) {
        if (CollectionUtils.isEmpty(chainNodes)) {
            return null;
        }
        List<ChainNode> positionNodes = chainNodes.stream().filter(node -> position.check(node.getPosition())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(positionNodes)) {
            return null;
        }
        return new SimpleChain(positionNodes);
    }
}
