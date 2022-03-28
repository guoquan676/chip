package com.template.chip.component.exception.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicReponse {
    private int errCode;
    private String errMsg;
    private Object data;
}
