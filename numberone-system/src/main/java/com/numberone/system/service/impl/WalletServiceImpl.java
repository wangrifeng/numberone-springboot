package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.Transaction;
import com.numberone.system.domain.Wallet;
import com.numberone.system.domain.WalletVo;
import com.numberone.system.mapper.TransactionMapper;
import com.numberone.system.mapper.WalletMapper;
import com.numberone.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-02-05
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {

    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper,TransactionMapper transactionMapper) {
        this.walletMapper = walletMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<WalletVo> getWallet(Map<String, Object> params) {
        return walletMapper.getWallets(params);
    }

    private static String walletStoreDir = "/data/mdc/userWallet";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult createWallet(int userId, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        System.out.println("开始创建钱包，默认地址为" + walletStoreDir);
        File file = new File(walletStoreDir);
        System.out.println(file.exists());
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = WalletUtils.generateNewWalletFile(password, new File(walletStoreDir), true);
        System.out.println("创建成功，钱包名为：" + name);
        String walletFilePath = walletStoreDir + "/" + name;
        Credentials credentials = WalletUtils.loadCredentials(password, walletFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
        Wallet wallet = new Wallet();
        wallet.setAddress(address);
        wallet.setWalletPath(walletFilePath);
        wallet.setUstdBlance(new BigDecimal(0));
        wallet.setMdcBlance(new BigDecimal(0));
        wallet.setPassword(password);
        wallet.setPublicKey(publicKey.toString());
        wallet.setPrivateKey(privateKey.toString());
        wallet.setCreateTime(new Date());
        wallet.setUserId(userId);
        walletMapper.insert(wallet);
        return AjaxResult.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult changeBalance(Map<String, Object> params) {
        EntityWrapper<Wallet> wrapper = new EntityWrapper<>();
        wrapper.eq("address", params.get("walletAddress"));
        Wallet wallet = walletMapper.selectList(wrapper).get(0);

        BigDecimal oldUsdtBalance = new BigDecimal(params.get("oldUsdtBalance").toString());
        BigDecimal usdtChange = new BigDecimal(params.get("usdtChange").toString());
        BigDecimal oldMdcBalance = new BigDecimal(params.get("oldMdcBalance").toString());
        BigDecimal MdcChange = new BigDecimal(params.get("MdcChange").toString());
        if ("0".equals(params.get("type"))) {
            wallet.setUstdBlance(oldUsdtBalance.add(usdtChange));
            wallet.setMdcBlance(oldMdcBalance.add(MdcChange));
            if(usdtChange.doubleValue() > 0){
                Transaction transaction = new Transaction();
                transaction.setCreateTime(new Date());
                transaction.setToAmount(usdtChange);
                transaction.setToUserId(wallet.getUserId());
                transaction.setToWalletAddress(wallet.getAddress());
                //0-usdt
                transaction.setToWalletType("0");
                //0-待交易
                transaction.setTransactionStatus("1");
                //0-充值
                transaction.setTransactionType("0");
                transaction.setRemark((String) params.get("remark"));
                transactionMapper.insert(transaction);
            }
            if(MdcChange.doubleValue() > 0){
                Transaction transaction = new Transaction();
                transaction.setCreateTime(new Date());
                transaction.setToAmount(MdcChange);
                transaction.setToUserId(wallet.getUserId());
                transaction.setToWalletAddress(wallet.getAddress());
                //0-usdt
                transaction.setToWalletType("1");
                //0-待交易
                transaction.setTransactionStatus("1");
                //0-充值
                transaction.setTransactionType("0");
                transaction.setRemark((String) params.get("remark"));
                transactionMapper.insert(transaction);
            }
        } else if ("1".equals(params.get("type"))) {
            wallet.setUstdBlance(oldUsdtBalance.subtract(usdtChange));
            wallet.setMdcBlance(oldMdcBalance.subtract(MdcChange));
            if(usdtChange.doubleValue() > 0){
                Transaction transaction = new Transaction();
                transaction.setCreateTime(new Date());
                transaction.setToAmount(usdtChange);
                transaction.setToUserId(wallet.getUserId());
                transaction.setToWalletAddress(wallet.getAddress());
                //0-usdt
                transaction.setToWalletType("0");
                //0-待交易
                transaction.setTransactionStatus("1");
                //0-充值
                transaction.setTransactionType("8");
                transactionMapper.insert(transaction);
            }
            if(MdcChange.doubleValue() > 0){
                Transaction transaction = new Transaction();
                transaction.setCreateTime(new Date());
                transaction.setToAmount(MdcChange);
                transaction.setToUserId(wallet.getUserId());
                transaction.setToWalletAddress(wallet.getAddress());
                //0-usdt
                transaction.setToWalletType("1");
                //0-待交易
                transaction.setTransactionStatus("1");
                //0-充值
                transaction.setTransactionType("8");
                transactionMapper.insert(transaction);
            }
        }
        walletMapper.update(wallet, wrapper);



        return AjaxResult.success();
    }

    @Override
    public Map<String,Object> balanceSum() {
        return walletMapper.getBalanceSum(new HashMap<>());
    }
}
