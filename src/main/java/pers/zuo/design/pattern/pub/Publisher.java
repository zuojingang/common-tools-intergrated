package pers.zuo.design.pattern.pub;

import java.util.Observable;

/**
 * @author zuojingang
 * @Title: Subject
 * @Description: Todo
 * @date 2019-08-06 18:08
 */
public class Publisher<T> extends Observable {

    private T notice;

    public T getNotice() {
        return notice;
    }

    public void setNotice(T notice) {
        this.notice = notice;
    }
}
