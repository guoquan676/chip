package com.template.chip.component.exception.resultenum;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:18
 */
public enum ErrorCode {
    /**
     * 成功
     */
    OK(0, "成功"),
    SYSTEM_ERROR(1001,"系统异常"),
    BAD_REQUEST(1002,"错误的请求参数"),
    NOT_FOUND(1003,"找不到请求路径！"),
    CONNECTION_ERROR(1004,"网络连接请求失败！"),
    METHOD_NOT_ALLOWED(1005,"不合法的请求方式"),
    DATABASE_ERROR(1004,"数据库异常"),
    BOUND_STATEMENT_NOT_FOUNT(1006,"找不到方法！"),
    REPEAT_REGISTER(1007,"重复注册"),
    NO_USER_EXIST(1008,"用户不存在"),
    INVALID_PASSWORD(1009,"密码错误"),
    NO_PERMISSION(1010,"非法请求！");
    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}