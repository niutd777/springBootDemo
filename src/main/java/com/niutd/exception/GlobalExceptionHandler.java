package com.niutd.exception;

import com.niutd.utils.Result;
import com.niutd.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理 只处理controller层抛出的异常
 *
 * @author: niutd
 * @date: 2019/3/14 17:23
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常捕捉处理
     *
     * @param e :
     * @return : com.niutd.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/14 18:05
     */
    @ExceptionHandler(value = Exception.class)
    public <T> Result<T> handle(Exception e) {
        logger.error("发生未处理的异常={}", e.getMessage(), e);
        return ResultUtil.error(0, e.getMessage() == null ? "网络异常，请稍后重试" : e.getMessage());
    }

    /**
     * 自定义异常处理  参数校验
     *
     * @param e :
     * @return : com.quickbusiness.utils.Result<T>
     * @author : niutd
     * @date : 2019/3/14 18:07
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public <T> Result<T> MethodArgumentNotValidHandler(HttpServletRequest request,
                                                       MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldErrors().get(0);
        logger.error("参数校验失败={}", error.getDefaultMessage(), e);
        return ResultUtil.error(10, error.getDefaultMessage());
    }


}
