package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.system.domain.InCome;
import com.numberone.system.mapper.InComeMapper;
import com.numberone.system.service.InComeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 收益servcieImpl
 */
@Service
public class InComeServiceImpl extends ServiceImpl<InComeMapper, InCome> implements InComeService {

    @Override
    public Map<Integer, Map<String,Object>> selectStaticIncomeGroupByLevel(Map<Integer, Map<String,Object>> levelIds, Date selDate, BigDecimal burnValue) {
        return this.baseMapper.selectStaticIncomeGroupByLevel(levelIds,selDate,burnValue.doubleValue());
    }

    @Override
    public Map<String, Object> getAdvanceShareSalary(Date selDate, Integer userId) {
        return this.baseMapper.getAdvanceShareSalary(selDate,userId);
    }

    @Override
    public BigDecimal getTotalSum(Integer userId, Date selDate, double burnValue) {
        return this.baseMapper.getTotalSum(userId,selDate,burnValue);
    }
}
