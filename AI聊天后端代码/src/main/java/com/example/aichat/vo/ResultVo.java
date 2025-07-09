package com.example.aichat.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVo<T> success() {
        ResultVo<T> result = new ResultVo<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    public static <T> ResultVo<T> success(T data) {
        ResultVo<T> result = new ResultVo<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> ResultVo<T> error(String message) {
        ResultVo<T> result = new ResultVo<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static <T> ResultVo<T> error(Integer code, String message) {
        ResultVo<T> result = new ResultVo<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}