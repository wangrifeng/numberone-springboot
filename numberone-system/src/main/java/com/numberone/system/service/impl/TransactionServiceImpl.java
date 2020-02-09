package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.system.domain.Transaction;
import com.numberone.system.domain.Wallet;
import com.numberone.system.mapper.TransactionMapper;
import com.numberone.system.mapper.WalletMapper;
import com.numberone.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final WalletMapper walletMapper;

    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper,WalletMapper walletMapper){
        this.transactionMapper = transactionMapper;
        this.walletMapper = walletMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void settlementIncome(String userId, String usdtMoney, String mdcMoney) {
        EntityWrapper<Wallet> walletEntityWrapper = new EntityWrapper<>();
        walletEntityWrapper.eq("user_id",userId);
        List<Wallet> walletList = walletMapper.selectList(walletEntityWrapper);
        if(walletList.size() > 0){
            Wallet wallet = walletList.get(0);
            //usdt收益
            if(new Double(usdtMoney)>0){
                BigDecimal usdtIncome = new BigDecimal(usdtMoney);
                BigDecimal usdtBalance = wallet.getUstdBlance();
                wallet.setUstdBlance(usdtBalance.add(usdtIncome));
                Transaction usdtTransaction = new Transaction();
                usdtTransaction.setToAmount(usdtIncome);
                usdtTransaction.setToUserId(Integer.parseInt(userId));
                usdtTransaction.setToWalletAddress(wallet.getAddress());
                usdtTransaction.setToWalletType("0");
                usdtTransaction.setTransactionStatus("1");
                usdtTransaction.setTransactionType("6");
                usdtTransaction.setCreateTime(new Date());
                usdtTransaction.setFeeAmount(new BigDecimal(0));
                transactionMapper.insert(usdtTransaction);
            }
            if(new Double(mdcMoney)>0){
                //mdc收益
                BigDecimal mdcIncome = new BigDecimal(mdcMoney);
                BigDecimal mdcBalance = wallet.getUstdBlance();
                wallet.setMdcBlance(mdcBalance.add(mdcIncome));
                Transaction mdcTransaction = new Transaction();
                mdcTransaction.setToAmount(new BigDecimal(mdcMoney));
                mdcTransaction.setToUserId(Integer.parseInt(userId));
                mdcTransaction.setToWalletAddress(wallet.getAddress());
                mdcTransaction.setToWalletType("1");
                mdcTransaction.setTransactionStatus("1");
                mdcTransaction.setTransactionType("6");
                mdcTransaction.setCreateTime(new Date());
                mdcTransaction.setFeeAmount(new BigDecimal(0));
                transactionMapper.insert(mdcTransaction);
            }
            walletMapper.updateById(wallet);
        }
    }
}
