package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.numberone.system.domain.User;
import com.numberone.system.mapper.UserMapper;
import com.numberone.system.service.UserLevelService;
import com.numberone.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private UserLevelService userLevelService;


    //用户账号停用状态
    private static final String USER_STATUS_FROZEN = "1";

    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserLevelService userLevelService) {
        this.userMapper = userMapper;
        this.userLevelService = userLevelService;
    }

    @Override
    public List<Integer> findAllUserIds() {
        return this.baseMapper.findAllUserIds();
    }

    @Override
    public List<User> getDirectUserLevel(String ids) {
        return this.baseMapper.getDirectUserLevel(ids);
    }


}
