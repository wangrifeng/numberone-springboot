package com.numberone.system.service;


import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.User;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 查询所有计算收益用户的id
     * @return
     */
    List<Integer> findAllUserIds();

    /**
     * 获取直接会员的信息
     * @param ids
     * @return
     */
    List<User> getDirectUserLevel(String ids);

    /**
     * 新增
     * @return
     */
    AjaxResult add(String userName, String loginName, String password, String walletPassword, Integer sendCode, Integer registerType);

    /**
     * 今日签约用户总数
     * @return
     */
    Integer todayNewPersonCount();
}
