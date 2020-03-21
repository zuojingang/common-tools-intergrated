package pers.zuo.design.pattern.template.constants.enums;

/**
 * @author zuojingang
 * @Title: Position
 * @Description: 位置枚举常量
 * @date 2019-12-16 18:19
 */
public enum Position {

    /**
     * BEFORE：前置责任链
     * AFTER：后置责任链
     */
    BEFORE(1), AFTER(2);

    public int code;

    Position(int code) {
        this.code = code;
    }

    public boolean check(Number code) {
        return null != code && code.equals(this.code);
    }
}
