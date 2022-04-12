package com.template.chip.controller.test;

import com.google.common.collect.Lists;
import com.template.chip.component.exception.annotation.JsonResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:53
 */
@RestController
@RequestMapping("/api")
public class ExceptionController {

    @GetMapping("/resourceNotFound")
    @JsonResponseBody
    public  List<Integer> throwException(String a) {
        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4);
        return integers;
    }
}