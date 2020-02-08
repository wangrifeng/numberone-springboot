package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.system.domain.Contract;

import java.util.Map;

/**
 * 合约Service
 */
public interface ContractService extends IService<Contract> {

    /**
     * 查询所有合约信息
     * @return
     */
    Map<Integer, Contract> selectAllContract();
}
