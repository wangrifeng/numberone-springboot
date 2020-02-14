package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.Transaction;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    List<Map<String,Object>> getTransaction(Map<String,Object> params);

    AjaxResult personHandleCashOut(Map<String,Object> params) throws InterruptedException, ExecutionException, CipherException, IOException;

    Map<String,Object> investCashOutSize();
}
