package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.numberone.system.domain.FeedBack;
import com.numberone.system.mapper.FeedBackMapper;
import com.numberone.system.service.FeedBackService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 反馈servcieImpl
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {

    @Override
    public List<FeedBack> list(String createBy, Date createTime) {
        return this.baseMapper.list(createBy,createTime);
    }

    @Override
    public int insertFeedBack(FeedBack feedBack) {
        return this.baseMapper.insertFeedBack(feedBack);
    }
}
