package pers.zuo.component.template.chain;

import com.itiaoling.hiccup.order.base.compont.template.chain.node.ChainNode;

/**
 * @author zuojingang
 * @Title: IChain
 * @Description: 责任链接口定义
 * @date 2019-12-16 16:34
 */
public interface IChain {

    ChainNode addNode(ChainNode node);

    default Chain subChain(Position position) {
        return null;
    }

    default <T> void exec(T params) {

    }
}
