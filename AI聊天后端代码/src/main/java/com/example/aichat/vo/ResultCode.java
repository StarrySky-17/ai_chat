package com.example.aichat.vo;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    USER_NOT_FOUND(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    NOT_LOGIN(1003, "请先登录"),
    NO_PERMISSION(1004, "没有权限"),
    CONVERSATION_NOT_FOUND(2001, "对话不存在"),
    MESSAGE_NOT_FOUND(2002, "消息不存在");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}