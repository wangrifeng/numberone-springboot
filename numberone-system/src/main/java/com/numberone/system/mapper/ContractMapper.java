package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.Contract;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

public interface ContractMapper extends BaseMapper<Contract> {

    /**
     * 查询所有的合约信息
     * @return
     */

    @MapKey("id")
    Map<Integer,Contract> selectAllContract();
}
