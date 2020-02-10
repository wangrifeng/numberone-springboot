package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.Contract;
import com.numberone.system.domain.UserContract;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserContractMapper extends BaseMapper<UserContract> {

    @Select("select sc.level,sc.type,sc.id,amount,income_rate as incomeRate from mdc_user_contract muc join sys_contract sc on sc.id = muc.contract_id where muc.user_id = #{userId} and sc.type = #{type}")
    Contract selectContractByUserId(@Param("userId") Integer userId, @Param("type") Integer type);

    /**
     * 查询用户是否已拥有对应的合约
     * @param userId
     * @param type
     * @return
     */
    UserContract getUserContractByTypeAndUserId(@Param("userId") Integer userId, @Param("type") Integer type);
}
