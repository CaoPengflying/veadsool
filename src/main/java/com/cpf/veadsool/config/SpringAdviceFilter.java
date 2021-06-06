package com.cpf.veadsool.config;

import com.cpf.veadsool.base.BusinessException;
import com.cpf.veadsool.base.ErrorConstant;
import com.cpf.veadsool.base.Result;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Reason: 拦截通用错误信息.
 *
 * @author Chen Lingang
 * @version $Id: SpringAdviceFilter, vo 0.1 16/8/8 上午11:26
 */
@ControllerAdvice
@RestControllerAdvice
public class SpringAdviceFilter {

    private static Logger logger = LoggerFactory.getLogger(SpringAdviceFilter.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Result.OnlyHintView.class)
    public @ResponseBody
    Result handlerIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) throws IOException {
        if (logger.isErrorEnabled()) {
            logger.error("『断言』业务断言错误: ", ex);
        }

        Result result = new Result();
        result.setStatus(ErrorConstant.FAIL);
        result.setText(ex.getMessage());
        result.setDescription(ex.getMessage());
        return result;
    }


    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Result.OnlyHintView.class)
    public @ResponseBody
    Result handlerBusinessException(BusinessException ex) {
        if (logger.isErrorEnabled()) {
            logger.error("『断言』业务断言错误: ", ex);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(baos));
        String message = ex.getMessage();
        return new Result(ex.getCode(), message, baos.toString());
    }

    /**
     * 验证异常
     * @param req
     * @param e
     * @return
     * @throws MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
        Result r = new Result();
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMesssage = new StringBuilder("Invalid Request:\n");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage.append(fieldError.getDefaultMessage()).append("\n");
        }
        r.setStatus(ErrorConstant.PARAM_IS_NULL);
        r.setText(errorMesssage.toString());
        logger.error("MethodArgumentNotValidException",e);
        return r;
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result handleBindException(BindException e) throws BindException {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        // 生成返回结果
        Result r = new Result();
        r.setStatus(ErrorConstant.PARAM_IS_NULL);
        String sb = fieldError.getField() + "=[" + fieldError.getRejectedValue() + "]" +
                fieldError.getDefaultMessage();
        r.setText(sb);
        logger.error("BindException", e);
        return r;
    }

}
