package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.numberone.system.domain.User;
import com.numberone.system.domain.UserContract;
import com.numberone.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ISysIndexServiceImpl implements ISysIndexService {
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    private UserService userService;
    @Autowired
    private UserContractService userContractService;
    @Autowired
    private InComeService inComeService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransactionService transactionService;
    @Override
    public Map<String, Object> mainInfo() {
        Future<Integer> userCountFuture = executor.submit(() -> {
            EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
            userEntityWrapper.eq("del_flag","0");
            return userService.selectCount(userEntityWrapper);
        });
        Future<Integer> userContractNumberFuture = executor.submit(() -> userContractService.selectSignCount());
        Future<Integer> todayNewPersonCountFuture = executor.submit(() -> userService.todayNewPersonCount());
        Future<BigDecimal> yesterdaySignIncomeCountFuture = executor.submit(() -> inComeService.yesterdaySignIncomeCount());
        Future<Map<String,Object>> balanceSum = executor.submit(() -> walletService.balanceSum());
        Future<Map<String,Object>> amountSum = executor.submit(() -> transactionService.investCashOutSize(new HashMap<>()));
        Map<String,Object> params = new HashMap<>();
        params.put("today","true");
        Future<Map<String,Object>> todayAmountSum = executor.submit(() -> transactionService.investCashOutSize(params));


        Map<String,Object> result = new HashMap<>();
        try {
            result.put("userCount",userCountFuture.get());
            result.put("userContractNumber",userContractNumberFuture.get());
            result.put("todayNewPersonCount",todayNewPersonCountFuture.get());
            result.put("yesterdaySignIncomeCount",yesterdaySignIncomeCountFuture.get());
            result.put("usdtBalance",balanceSum.get().get("usdtBalance"));
            result.put("mdcBalance",balanceSum.get().get("mdcBalance"));
            result.put("invest",amountSum.get().get("invest"));
            result.put("cashOut",amountSum.get().get("cashOut"));
            result.put("todayInvest",todayAmountSum.get().get("invest"));
            result.put("todayCashOut",todayAmountSum.get().get("cashOut"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
