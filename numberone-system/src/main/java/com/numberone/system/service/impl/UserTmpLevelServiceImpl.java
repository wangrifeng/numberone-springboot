package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.UserTmpLevel;
import com.numberone.system.mapper.UserTmpLevelMapper;
import com.numberone.system.service.ISysUserService;
import com.numberone.system.service.UserTmpLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UserTmpLevelServiceImpl extends ServiceImpl<UserTmpLevelMapper, UserTmpLevel> implements UserTmpLevelService {

    @Autowired
    private ISysUserService sysUserService;
    @Override
    public List<UserTmpLevel> list(Map<String, Object> params) {
        return this.baseMapper.list(params);
    }

    @Override
    public AjaxResult add(String loginName, Integer level, Integer number) {
        SysUser sysUser = sysUserService.selectUserByLoginName(loginName);
        if(sysUser == null){
            return  AjaxResult.error("该账号不存在");
        }
        Date date = new Date();
        Integer userId = sysUser.getUserId().intValue();
        //计算过期时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, number); //当前时间减去一天，即一天前的时间
        Date outDate = instance.getTime();

        //查询用户是否存在未过期优惠
        UserTmpLevel noOutDate = this.findNoOutDateTmpLevel(userId,date);
        if(noOutDate == null){
            //新增
            UserTmpLevel userTmpLevel = new UserTmpLevel();
            userTmpLevel.setUserId(userId);
            userTmpLevel.setLevel(level);
            userTmpLevel.setOutDate(outDate);
            userTmpLevel.setCreateTime(date);
            this.baseMapper.insert(userTmpLevel);
        }else{
            //更新
            noOutDate.setOutDate(outDate);
            noOutDate.setLevel(level);
            this.baseMapper.updateById(noOutDate);
        }
        return AjaxResult.success();
    }

    @Override
    public UserTmpLevel findNoOutDateTmpLevel(Integer userId, Date date) {
        return this.baseMapper.findNoOutDateTmpLevel(userId,date);
    }
}
