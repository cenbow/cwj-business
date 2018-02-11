package com.mm.dev.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.util.JsonMapper;
import com.mm.dev.entity.wechat.ReturnMsg;
import com.mm.dev.enums.ExceptionEnum;
import com.mm.dev.wechatUtils.ReturnMsgUtil;

/**
 * @ClassName: ExceptionHandle 
 * @Description: 统一异常处理类
 * @author zhangxy
 * @date 2017年8月24日 上午9:41:42
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnMsg<Object> handle(HttpServletRequest request,Exception e) {
    	String requestURL = request.getRequestURL().toString();
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            logger.error(serviceException.getMessage(),e);
            return ReturnMsgUtil.error(serviceException.getCode(), serviceException.getMessage(),requestURL);
        }else if (e instanceof BindException) {
        	BindException bindException = (BindException) e;
        	BindingResult bindingResult = bindException.getBindingResult();
        	Map<String,String> errorMsg = new HashMap<>();
        	if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    errorMsg.put(fieldError.getObjectName() + "." + fieldError.getField(),
                            fieldError.getDefaultMessage());
                }
            }
            logger.error(JsonMapper.toJsonString(errorMsg),e);
            return ReturnMsgUtil.error(ExceptionEnum.param_not_null.getCode(), JsonMapper.toJsonString(errorMsg),requestURL);
        } else {
            logger.error("【系统异常】{}", e);
            return ReturnMsgUtil.error(ExceptionEnum.system_error.getCode(), ExceptionEnum.system_error.getMsg(),requestURL);
        }
    }
}
