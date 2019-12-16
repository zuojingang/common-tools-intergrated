package pers.zuo.component.template;

import com.itiaoling.hiccup.order.base.compont.template.chain.Chain;

/**
 * @author zuojingang
 * @Title: TemplateWithChain
 * @Description: 模版模式与责任链模式整合类，包含执行模版以及责任链
 * @date 2019-12-16 16:41
 */
public class TemplateWithChain<T> {

    private final Chain chain;
    private final Template<T> template;

    public TemplateWithChain(Template<T> template, Chain chain) {
        this.template = template;
        this.chain = chain;
    }

    public T compose(){
        return template.withChain(chain);
    }
}
