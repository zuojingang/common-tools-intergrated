package pers.zuo.component.template;

import com.itiaoling.hiccup.order.base.compont.template.chain.Chain;

/**
 * @author zuojingang
 * @Title: Template
 * @Description: 执行模版接口
 * @date 2019-12-16 17:36
 */
public interface Template<T> {

    T withChain(Chain chain);
}
