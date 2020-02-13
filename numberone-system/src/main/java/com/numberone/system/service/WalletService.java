package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.system.domain.Wallet;

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

}
