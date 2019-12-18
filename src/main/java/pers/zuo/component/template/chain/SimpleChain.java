package pers.zuo.component.template.chain;

import org.apache.commons.collections4.CollectionUtils;
import pers.zuo.component.template.chain.node.ChainNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zuojingang
 * @Title: SimpleChain
 * @Description: 简单的责任链类
 * @date 2019-12-16 17:31
 */
public class SimpleChain extends Chain {

    private final List<ChainNode> chainNodes = new ArrayList<>();

    public SimpleChain() {
    }

    public SimpleChain(List<ChainNode> chainNodes) {
        this.chainNodes.addAll(chainNodes);
    }

    @Override
    public <T> void exec(T param) {
        if (CollectionUtils.isEmpty(chainNodes)) {
            return;
        }
        /*
          在执行责任链之前，先进行节点排序
         */
        chainNodes.sort(Comparator.comparingInt(ChainNode::getOrder));
        int idx = 0;
        do {
            ChainNode node = chainNodes.get(idx);
            /*
              如果当前节点执行条件不来满足，或者当前这一步执行成功
              继续执行下一步
             */
            if (!node.check(param) || node.exec(param)) {
                continue;
            }
            return;
        } while (idx < chainNodes.size());
    }
}
