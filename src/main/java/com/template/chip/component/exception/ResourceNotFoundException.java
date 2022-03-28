package com.template.chip.component.exception;

import com.template.chip.component.exception.resultenum.ErrorCode;

import java.util.Map;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:23
 */
public class ResourceNotFoundException extends BaseException{
    public ResourceNotFoundException(Map<String, Object> data) {
        super(ErrorCode.NOT_FOUND, data);
    }
}
