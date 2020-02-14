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

import java.math.BigDecimal;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult changeBalance(Map<String, Object> params) {
        EntityWrapper<Wallet> wrapper = new EntityWrapper<>();
        wrapper.eq("address",params.get("walletAddress"));
        Wallet wallet = new Wallet();
        if("0".equals(params.get("type"))){
            wallet.setUstdBlance(new BigDecimal(params.get("oldUsdtBalance").toString()).add(new BigDecimal(params.get("usdtChange").toString())));
            wallet.setMdcBlance(new BigDecimal(params.get("oldMdcBalance").toString()).add(new BigDecimal(params.get("MdcChange").toString())));
        }else if("1".equals(params.get("type"))){
            wallet.setUstdBlance(new BigDecimal(params.get("oldUsdtBalance").toString()).subtract(new BigDecimal(params.get("usdtChange").toString())));
            wallet.setMdcBlance(new BigDecimal(params.get("oldMdcBalance").toString()).subtract(new BigDecimal(params.get("MdcChange").toString())));
        }
        walletMapper.update(wallet,wrapper);
        return AjaxResult.success();
    }
}
