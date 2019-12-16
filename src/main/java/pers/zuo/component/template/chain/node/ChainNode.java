package pers.zuo.component.template.chain.node;

/**
 * @author zuojingang
 * @Title: ChainNode
 * @Description: Todo
 * @date 2019-12-16 18:12
 */
public interface ChainNode<T> {

    /**
     * 检查是否满足执行该节点的要求
     */
    default boolean check(T param) {
        return true;
    }

    /**
     * 节点执行的具体内容
     */
    boolean exec(T param);

    /**
     * @return 节点在链上的位置，用来确定链中节点的执行顺序，
     * 如需要保证节点执行顺序，则必须重写
     */
    default int getOrder(){
        return 0;
    }

    /**
     * @return 节点所属位置
     */
    int getPosition();
}
