package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.system.domain.Contract;
import com.numberone.system.mapper.ContractMapper;
import com.numberone.system.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

    @Override
    public Map<Integer, Contract> selectAllContract() {
        return this.baseMapper.selectAllContract();
    }
}
