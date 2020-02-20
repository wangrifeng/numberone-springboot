package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.UserTmpLevel;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface UserTmpLevelService extends IService<UserTmpLevel> {

    /**
     * 列表
     * @param params
     * @return
     */
    List<UserTmpLevel> list(Map<String, Object> params);

    /**
     * 新增临时等级
     * @param loginName
     * @param level
     * @param number
     * @return
     */
    AjaxResult add(String loginName, Integer level, Integer number);

    /**
     * 查询未过期的优惠等级
     * @param userId
     * @param date
     * @return
     */
    UserTmpLevel findNoOutDateTmpLevel(Integer userId, Date date);
}
