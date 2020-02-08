package com.numberone.system.service;


import com.numberone.common.exception.BusinessException;
import com.numberone.system.domain.Contract;

import java.util.Date;
import java.util.Map;

/**
 * 奖金计算
 */
public interface RewardService {

    /**
     * 计算用户的合约收益
     * @param userId
     * @param contractCache
     * @param selDate
     * @throws BusinessException
     */
    void calculateContractSalary(Integer userId, Map<Integer, Contract> contractCache, Date selDate) throws BusinessException;

    /**
     * 计算用户的分享收益
     * @param userId
     * @param contractCache
     * @param selDate
     */
    void calculateShareSalary(Integer userId, Map<Integer, Contract> contractCache, Date selDate);
}
