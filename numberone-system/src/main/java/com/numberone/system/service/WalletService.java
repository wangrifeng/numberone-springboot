package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.Wallet;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
public interface WalletService extends IService<Wallet> {

    List<Map<String,Object>> getWallet(Map<String,Object> params);

    AjaxResult changeBalance(Map<String,Object> params);

    /**
     * 创建钱包
     * @param userId 用户信息
     * @return ResponseResult
     */
    AjaxResult createWallet(int userId, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException;
}
