package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.Wallet;
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

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Override
    public List<Map<String, Object>> getWallet(Map<String, Object> params) {
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
        Wallet wallet = new Wallet();
        if ("0".equals(params.get("type"))) {
            wallet.setUstdBlance(new BigDecimal(params.get("oldUsdtBalance").toString()).add(new BigDecimal(params.get("usdtChange").toString())));
            wallet.setMdcBlance(new BigDecimal(params.get("oldMdcBalance").toString()).add(new BigDecimal(params.get("MdcChange").toString())));
        } else if ("1".equals(params.get("type"))) {
            wallet.setUstdBlance(new BigDecimal(params.get("oldUsdtBalance").toString()).subtract(new BigDecimal(params.get("usdtChange").toString())));
            wallet.setMdcBlance(new BigDecimal(params.get("oldMdcBalance").toString()).subtract(new BigDecimal(params.get("MdcChange").toString())));
        }
        walletMapper.update(wallet, wrapper);
        return AjaxResult.success();
    }
}
