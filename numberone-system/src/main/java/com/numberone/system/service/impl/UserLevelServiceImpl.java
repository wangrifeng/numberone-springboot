package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.numberone.system.domain.UserLevel;
import com.numberone.system.mapper.UserLevelMapper;
import com.numberone.system.service.UserLevelService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {

    @Override
    public void addLevelRelation(String recIds, String recedId) {
        if (StringUtils.isEmpty(recIds)) {
            return;
        }
        String[] split = recIds.split(",");
        List<UserLevel> userLevels = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            UserLevel userLevel = new UserLevel(split[i], recedId, split.length - i, new Date());
            userLevels.add(userLevel);
        }
        this.baseMapper.batchInsert(userLevels);
    }

    @Override
    public Map<Integer,  Map<String,Object>> selectRecedUserIds(Integer userId) {
        return this.baseMapper.selectRecedUserIds(userId);
    }

    @Override
    public BigDecimal getTotalSum(Integer userId) {
        return this.baseMapper.getTotalSum(userId);
    }

    @Override
    public List<Integer> selectRecIdsByRecedId(Integer userId) {
        return this.baseMapper.selectRecIdsByRecedId(userId);
    }
}
