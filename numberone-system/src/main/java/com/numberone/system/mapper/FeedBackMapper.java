package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.FeedBack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public interface FeedBackMapper extends BaseMapper<FeedBack> {

    /**
     * 查询反馈列表
     * @return
     */
    List<FeedBack> list(@Param("createBy")String createBy,@Param("createTime")Date createTime);

    /**
     * 新增反馈
     * @param feedBack
     * @return
     */
    public int insertFeedBack(FeedBack feedBack);
}
