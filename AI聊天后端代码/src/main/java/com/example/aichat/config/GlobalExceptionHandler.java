package com.example.aichat.config;

import com.example.aichat.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常: {}", e.getMessage());
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVo handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("404异常: {}", e.getMessage());
        return ResultVo.error(404, "接口不存在: " + request.getRequestURI());
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVo handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        log.error("空指针异常: {}", e.getMessage(), e);
        return ResultVo.error(500, "系统异常，请联系管理员");
    }

    /**
     * 处理其他所有异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {}", e.getMessage(), e);
        return ResultVo.error(500, "系统异常，请联系管理员");
    }

    /**
     * 自定义业务异常类
     */
    public static class BusinessException extends RuntimeException {
        private int code;

        public BusinessException(int code, String message) {
            super(message);
            this.code = code;
        }

        public BusinessException(String message) {
            this(500, message);
        }

        public int getCode() {
            return code;
        }
    }
}