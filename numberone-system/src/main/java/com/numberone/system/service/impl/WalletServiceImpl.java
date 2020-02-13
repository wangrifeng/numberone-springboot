package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.system.domain.Wallet;
import com.numberone.system.mapper.WalletMapper;
import com.numberone.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, Wallet> implements WalletService {

    private final WalletMapper walletMapper;

    @Autowired
    public WalletServiceImpl(WalletMapper walletMapper){
        this.walletMapper =walletMapper;
    }

    @Override
    public List<Map<String, Object>> getWallet(Map<String, Object> params) {
        return walletMapper.getWallets(params);
    }
}
