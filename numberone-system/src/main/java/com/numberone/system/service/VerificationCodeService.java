package com.numberone.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.numberone.common.exception.BusinessException;
import com.numberone.system.domain.VerificationCode;

public interface VerificationCodeService extends IService<VerificationCode> {

    /**
     * 短信推送
     * @return
     * @param userId
     */
    Integer getVerificationCode(Integer userId) throws BusinessException;

    /**
     * 获取email验证码
     * @return
     * @param email
     */
    Integer getEmailVerificationCode(String email) throws BusinessException;

    /**
     * 验证验证码是狗正确
     * @param verCode
     * @param verId
     * @return
     */
    boolean validateVerCode(String verCode, String verId);

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    Integer getPhoneVerificationCode(String phone) throws BusinessException;
}
