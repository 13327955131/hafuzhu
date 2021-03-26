package com.hoostec.hfz.config.exception;

import com.hoostec.hfz.utils.ApiResultDataUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常捕获
 *
 * @author loo
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDataUtil badRequestException(IllegalArgumentException exception) {
        return ResultDataUtil.isError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultDataUtil badRequestException(AccessDeniedException exception) {
        return ResultDataUtil.isError(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDataUtil badRequestException(Exception exception) {
        return ResultDataUtil.isError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultDataUtil exception(Throwable throwable) {
        log.error("系统异常", throwable);
        return ResultDataUtil.isError(HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage());

    }

    @ExceptionHandler(BindException.class)
    public ApiResultDataUtil handleBindException(BindException ex) {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = ex.getFieldError();
        // 生成返回结果
        return new ApiResultDataUtil(400, fieldError.getDefaultMessage(), null);
    }
}
