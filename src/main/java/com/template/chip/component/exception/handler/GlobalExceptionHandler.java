package com.template.chip.component.exception.handler;

import com.template.chip.component.exception.BaseException;
import com.template.chip.component.exception.ResourceNotFoundException;
import com.template.chip.component.exception.annotation.JsonResponseBody;
import com.template.chip.component.exception.reponse.BasicReponse;
import com.template.chip.component.exception.reponse.ErrorReponse;
import com.template.chip.component.exception.resultenum.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:25
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Exception.class)
    public ErrorReponse handleException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        BaseException baseException = new BaseException(ErrorCode.SYSTEM_ERROR,null);
        return new ErrorReponse(baseException, request.getRequestURI());
    }

    @ExceptionHandler(BaseException.class)
    public ErrorReponse handleBaseException(BaseException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return  new ErrorReponse(ex, request.getRequestURI());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ErrorReponse handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return  new ErrorReponse(ex, request.getRequestURI());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        JsonResponseBody focusController = AnnotationUtils.findAnnotation(annotatedElement, JsonResponseBody.class);
        return focusController != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        BasicReponse basicReponse = new BasicReponse();
        basicReponse.setErrCode(ErrorCode.OK.getCode());
        basicReponse.setErrMsg(ErrorCode.OK.name());
        basicReponse.setData(body);
        return basicReponse;
    }
}
