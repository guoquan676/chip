package com.template.chip.component.exception.reponse;

import com.template.chip.component.exception.BaseException;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:21
 */
@Data
public class ErrorReponse extends BasicReponse {

    private String path;
    private Instant timestamp;
    private Map<String, Object> errorDataInfo = new HashMap<String, Object>();

    public ErrorReponse() {
    }

    public ErrorReponse(BaseException ex, String path) {
        this(ex.getError().getCode(), ex.getError().getMessage(), path, ex.getData());
    }

    public ErrorReponse(int code, String message, String path, Map<String, Object> errorDataInfo) {
        super(code,message,null);
        this.path = path;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(errorDataInfo)) {
            this.errorDataInfo.putAll(errorDataInfo);
        }
    }

    @Override
    public String toString() {
        return "ErrorReponse{" +
                "code=" + this.getErrCode() +
                ", message='" + this.getErrMsg() + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + errorDataInfo +
                '}';
    }
}
