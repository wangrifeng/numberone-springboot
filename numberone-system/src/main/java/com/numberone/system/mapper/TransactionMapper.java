package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.ContractVo;
import com.numberone.system.domain.InvestCashOutVo;
import com.numberone.system.domain.Transaction;
import com.numberone.system.domain.TransactionVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Component
public interface TransactionMapper extends BaseMapper<Transaction> {

    List<Map<String,Object>> getTransaction(Map<String,Object> params);

    List<Map<String,Object>> transactionAmountSum(Map<String,Object> params);

    List<ContractVo> getContract(Map<String,Object> params);

    List<TransactionVo> exportTransaction(Map<String,Object> params);

    List<InvestCashOutVo> exportInvestCashOut(Map<String,Object> params);


}
