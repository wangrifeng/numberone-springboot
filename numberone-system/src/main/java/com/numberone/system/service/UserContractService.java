package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.exception.BusinessException;
import com.numberone.system.domain.Contract;
import com.numberone.system.domain.UserContract;

/**
 * 用户合约关系Service
 */
public interface UserContractService extends IService<UserContract> {

    /**
     * 查询用户合约卡详情
     * @param userId
     * @param type
     * @return
     */
    Contract selectContractByUserId(Integer userId, Integer type);


    /**
     * 查询用户是否已拥有对应的合约
     * @param userId
     * @param type
     * @return
     */
    UserContract getUserContractByTypeAndUserId(Integer userId, Integer type);
}
