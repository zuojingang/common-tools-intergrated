package pers.zuo.lambda;

/**
 * @author zuojingang
 * @Title: TransformActuator
 * @Description: 对象转换执行器lambda
 * @date 2020/3/15 14:58
 */
public interface TransformActuator<I, O> {

    O execute(I param);
}
