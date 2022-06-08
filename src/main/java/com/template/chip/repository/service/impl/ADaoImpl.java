package com.template.chip.repository.service.impl;

import com.template.chip.repository.po.APO;
import com.template.chip.repository.mapper.IAMapper;
import com.template.chip.repository.service.IADao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.template.chip.work.utils.CoodinateCovertor;
import com.template.chip.work.utils.Gps;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guozhenquan
 * @since 2022-02-26
 */
@Service
public class ADaoImpl extends ServiceImpl<IAMapper, APO> implements IADao {
    public static void main(String[] args) {
        Gps gps = CoodinateCovertor.bd09_To_Gcj02(Double.parseDouble("30.196756595"),Double.parseDouble("120.186926195"));
        System.out.println(gps);
    }
}