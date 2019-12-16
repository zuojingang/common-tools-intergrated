package pers.zuo.component.template.chain.node;

/**
 * @author zuojingang
 * @Title: SimpleChainNode
 * @Description: 简单的责任链节点定义
 * @date 2019-12-16 16:00
 */
public interface SimpleChainNode extends ChainNode {

    /**
     * 单一位置的责任链，位置属性可忽略
     * @return
     */
    @Override
    default int getPosition(){
        return 0;
    }
}
