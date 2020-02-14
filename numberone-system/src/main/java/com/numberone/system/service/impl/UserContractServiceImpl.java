package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.common.exception.BusinessException;
import com.numberone.system.domain.Contract;
import com.numberone.system.domain.User;
import com.numberone.system.domain.UserContract;
import com.numberone.system.mapper.UserContractMapper;
import com.numberone.system.service.ContractService;
import com.numberone.system.service.UserContractService;
import com.numberone.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户合约servcieImpl
 */
@Service
public class UserContractServiceImpl extends ServiceImpl<UserContractMapper, UserContract> implements UserContractService {

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Override
    public Contract selectContractByUserId(Integer userId, Integer type) {
        return this.baseMapper.selectContractByUserId(userId,type);
    }

    @Override
    public UserContract getUserContractByTypeAndUserId(Integer userId, Integer type) {
        return this.baseMapper.getUserContractByTypeAndUserId(userId,type);
    }

    @Override
    public Integer selectSignCount() {
        return this.baseMapper.selectSignCount();
    }
}
