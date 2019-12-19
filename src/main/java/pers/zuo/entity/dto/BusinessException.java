package pers.zuo.entity.dto;

import java.io.Serializable;

/**
 * @author zuojingang
 * @Title: BusinessException
 * @Description: Todo
 * @date 2019-12-19 11:45
 */
public class BusinessException extends RuntimeException implements Serializable {
    protected String errCode;
    private Object[] arguments;

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
        this.errCode = msg;
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(String code, String msg) {
        super(msg);
        this.errCode = code;
    }

    public BusinessException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.errCode = code;
    }

    public BusinessException(String code, Object... args) {
        this.errCode = code;
        this.arguments = args;
    }

    public BusinessException(String code, String msg, Object... args) {
        super(msg);
        this.errCode = code;
        this.arguments = args;
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public void setErrorArguments(Object... args) {
        this.arguments = args;
    }

    public Object[] getErrorArguments() {
        return this.arguments;
    }

    public String getErrorCode() {
        return this.errCode;
    }
}
