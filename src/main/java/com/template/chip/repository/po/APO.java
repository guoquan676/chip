package com.template.chip.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author guozhenquan
 * @since 2022-02-26
 */
@Getter
@Setter
@TableName("t_a")
public class APO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String companyName;

    private LocalDateTime createdTime;

    private Integer userId;

    private Integer customerId;

    private Integer status;

    private String jsonstr;
}
