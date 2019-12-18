package pers.zuo.component.template.chain;

/**
 * @author zuojingang
 * @Title: Position
 * @Description: 位置枚举常量
 * @date 2019-12-16 18:19
 */
public enum Position {

    /**
     * BEFORE_ERGODIC：遍历前
     * EACH_ROW：遍历时
     * AFTER_ERGODIC：遍历后
     */
    BEFORE_ERGODIC(1), EACH_ROW(2), AFTER_ERGODIC(3);

    public int code;

    Position(int code) {
        this.code = code;
    }

    public boolean check(Number code) {
        return null != code && code.equals(this.code);
    }
}
