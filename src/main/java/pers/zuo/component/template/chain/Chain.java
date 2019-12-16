package pers.zuo.component.template.chain;

import com.itiaoling.hiccup.order.base.compont.template.chain.node.ChainNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuojingang
 * @Title: Chain
 * @Description: 责任链抽象类定义
 * @date 2019-12-16 16:34
 */
public abstract class Chain implements IChain {

    protected final List<ChainNode> chainNodes = new ArrayList<>();

    @Override
    public ChainNode addNode(ChainNode node) {
        chainNodes.add(node);
        return node;
    }

}
