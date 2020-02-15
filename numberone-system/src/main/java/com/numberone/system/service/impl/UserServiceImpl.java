package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.exception.BusinessException;
import com.numberone.common.utils.Md5Utils;
import com.numberone.system.domain.User;
import com.numberone.system.mapper.UserMapper;
import com.numberone.system.service.UserLevelService;
import com.numberone.system.service.UserService;
import com.numberone.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private UserLevelService userLevelService;
    @Autowired
    private WalletService walletService;


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

    private void validatePayPassword(String payPassword) throws BusinessException {
        if(payPassword == null || payPassword.length() != 6){
            throw new BusinessException("支付密码的长度只能为6");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(String userName, String loginName, String password, String walletPassword, Integer sendCode, Integer registerType) {
        try{
            validatePayPassword(walletPassword);
        }catch (BusinessException e){
            return AjaxResult.error(e.getMessage());
        }

        //count>0说明username已存在，isRepeat>0说明姓名已存在，重复需要加标识
        Integer count = userMapper.user(loginName);
        Integer isRepeat = userMapper.isRepeat(userName);
        if (count > 0) {
            //登录名称重复
            return AjaxResult.error("登录名称重复");
        } else if (isRepeat > 0) {
            //用户名称重复
            return AjaxResult.error("用户名称重复");
        } else if (sendCode == null) {
            //推荐码不存在
            return AjaxResult.error("推荐码不存在");
        } else {
            // 新增用户
            //获取推送人id
            Map<String, Object> sendUser = userMapper.getUserBySendCode(sendCode);
            if (sendUser == null || sendUser.get("userId") == null) {
                //推送码失效
                return AjaxResult.error("推送码失效");
            }
            String sendUserId = sendUser.get("userId").toString();
            //生成6位随机的邀请码
            int random = (int) ((Math.random() * 9 + 1) * 100000);
            //新增用户
            User tbUser = new User();
            tbUser.setUserName(userName);
            tbUser.setLoginName(loginName);
            tbUser.setDelFlag(0);
            tbUser.setCreateTime(new Date());
            tbUser.setUpdateTime(new Date());
            tbUser.setRegisterType(registerType);
            if (registerType == 0) {
                tbUser.setEmail(loginName);
            } else {
                tbUser.setPhoneNumber(loginName);
            }
            tbUser.setPassword(Md5Utils.hash(loginName+ password));
            tbUser.setSendCode(random);
            tbUser.setUpUserId(sendUserId);
            tbUser.setPayPassword(Md5Utils.hash(loginName+ walletPassword));
            //获取所有人推荐人id
            String upUserIds = "";
            Object sendUpUserIdsObj = sendUser.get("upUserIds");
            if (sendUser.get("upUserId") == null) {
                //推荐人本身没有推荐人 最顶级
                upUserIds = sendUserId;
            } else {
                //推荐人还有推荐人
                upUserIds = sendUpUserIdsObj.toString() + "," + sendUserId;
            }
            tbUser.setUpUserIds(upUserIds);
            int userCount = userMapper.insert(tbUser);

            //新增用户层级关系
            userLevelService.addLevelRelation(upUserIds, tbUser.getId());

            //新增用户角色中间表
            String userId = tbUser.getId();
//            String roleId = map.get("roleId").toString();
//            if (StringUtils.isNotEmpty(roleId)) {
//                String[] arr = roleId.split(",");
//                for (String string : arr) {
//                    RoleUser roleUser = new RoleUser();
//                    roleUser.setRoleId(string);
//                    roleUser.setUserId(userId);
//                    roleUserMapper.insert(roleUser);
//                }
//            }

            //更新推荐人的团队成员总数
            this.updateRecMemberSize(sendUserId);

            //添加钱包
            try {
                walletService.createWallet(Integer.parseInt(userId), walletPassword);
            } catch (Exception e) {
                return AjaxResult.error();
            }
            return userCount == 1 ? AjaxResult.success() : AjaxResult.error();
        }
    }

    @Override
    public Integer todayNewPersonCount() {
        return this.baseMapper.todayNewPersonCount();
    }

    /**
     * 更新推荐人的团队成员总数
     *
     * @param userId
     */
    private void updateRecMemberSize(String userId) {
        Integer memberSize = userLevelService.selectMemberSizeByUserId(userId);
        User user = new User();
        user.setId(userId);
        user.setMemberSize(memberSize);
        this.updateById(user);

        User u = this.selectById(userId);
        if (u == null || u.getUpUserId() == null) {
            return;
        }
        //如果存在推荐人 更新推荐人的 成员个数
        this.updateRecMemberSize(u.getUpUserId());
    }


}
