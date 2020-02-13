package com.numberone.system.service;


import com.baomidou.mybatisplus.service.IService;
import com.numberone.system.domain.FeedBack;
import java.util.Date;
import java.util.List;

/**
 * 反馈service
 */
public interface FeedBackService extends IService<FeedBack> {

    /**
     * 反馈列表
     * @return
     */
    List<FeedBack> list(String createBy, Date createTime);

    /**
     * 新增反馈
     * @return
     */
    public int insertFeedBack(FeedBack feedBack);
}
