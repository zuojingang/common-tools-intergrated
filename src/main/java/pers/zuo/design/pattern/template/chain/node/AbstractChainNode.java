package pers.zuo.design.pattern.template.chain.node;

import org.springframework.context.ApplicationContext;
import pers.zuo.design.pattern.template.constants.enums.Position;

import java.util.Objects;

/**
 * @author zuojingang
 * @Title: AbstractChainNode
 * @Description: Todo
 * @date 2020/3/17 19:31
 */
public abstract class AbstractChainNode<P> implements IChainNode<P> {

    protected ApplicationContext applicationContext;
    protected Integer order;
    protected Position position;

    @Override
    public IChainNode<P> withApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    @Override
    public IChainNode<P> withOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Override
    public int getOrder() {
        if (Objects.nonNull(order)) {
            return order;
        }
        return 0;
    }

    @Override
    public IChainNode<P> withPosition(Position position) {
        this.position = position;
        return this;
    }

    @Override
    public Position getPosition() {
        if (Objects.nonNull(position)) {
            return position;
        }
        return Position.BEFORE;
    }
}
