package pers.zuo.design.pattern.template.chain.node;

import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.constants.enums.Position;

/**
 * @author zuojingang
 * @Title: IChainNode
 * @Description: 责任链节点接口
 * @date 2019-12-16 18:12
 */
public interface IChainNode<P> {

    /**
     * 设置应用上下文
     */
    IChainNode<P> withApplicationContext(ApplicationContext applicationContext);

    /**
     * 检查是否满足执行该节点的要求
     */
    default boolean check(P params) {
        return true;
    }

    /**
     * 节点执行的具体内容
     */
    boolean exec(P params);

    /**
     * 设置节点顺序，如果不设置，默认为0
     * 如需要保证节点执行顺序，则必须设置
     */
    IChainNode<P> withOrder(Integer order);

    /**
     * @return 节点在链上的位置，用来确定链中节点的执行顺序
     */
    int getOrder();

    /**
     * 设置节点位置，如果不设置，默认为前置节点
     */
    IChainNode<P> withPosition(Position position);

    /**
     * @return 节点所属位置
     */
    Position getPosition();
}
