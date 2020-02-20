package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.UserTmpLevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface UserTmpLevelMapper extends BaseMapper<UserTmpLevel> {

    /**
     * 查询用户临时等级列表
     * @param params
     * @return
     */
    List<UserTmpLevel> list(@Param("map") Map<String, Object> params);

    /**
     * 查询用户是否有未过期优惠
     * @param userId
     * @param date
     * @return
     */
    UserTmpLevel findNoOutDateTmpLevel(@Param("userId") Integer userId,@Param("date") Date date);
}
