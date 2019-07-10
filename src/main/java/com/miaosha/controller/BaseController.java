package com.miaosha.controller;

import com.miaosha.error.BussinessException;
import com.miaosha.error.EmBussinessError;
import com.miaosha.response.CommentReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded" ;
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception exception){
        CommentReturnType commentReturnType = new CommentReturnType();
        Map<String, Object> responseData = new HashMap<String, Object>();
        if(exception instanceof BussinessException) {
            BussinessException bussinessException = (BussinessException) exception;
            responseData.put("errCode", bussinessException.getErrCode());
            responseData.put("errMsg", bussinessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBussinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBussinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommentReturnType.create(responseData,"fail");

    }
}
