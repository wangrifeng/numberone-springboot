package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.system.domain.Transaction;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
public interface TransactionService extends IService<Transaction> {

    /**
     * 每日收益结算交易并修改余额
     * @param userId 用户id
     * @param usdtMoney usdt收益
     * @param mdcMoney mdc收益
     */
    void settlementIncome(String userId,String usdtMoney,String mdcMoney);

}
