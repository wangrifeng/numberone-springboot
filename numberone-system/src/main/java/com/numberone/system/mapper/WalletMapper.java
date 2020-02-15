package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.Wallet;
import com.numberone.system.domain.WalletVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Component
public interface WalletMapper extends BaseMapper<Wallet> {

    List<WalletVo> getWallets(Map<String,Object> params);

    Map<String,Object> getBalanceSum(Map<String,Object> params);

}
