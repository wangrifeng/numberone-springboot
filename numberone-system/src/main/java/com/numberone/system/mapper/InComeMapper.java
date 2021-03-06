package com.numberone.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.numberone.system.domain.InCome;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InComeMapper extends BaseMapper<InCome> {

    /**
     * 查询所有被推荐人收益分代总和
     * @param levelIds
     * @param selDate
     * @param burnValue 烧伤值
     * @return
     */
    @MapKey("level")
    Map<Integer, Map<String,Object>> selectStaticIncomeGroupByLevel(@Param("map") Map<Integer, Map<String, Object>> levelIds, @Param("selDate") Date selDate, @Param("burnValue") double burnValue);

    /**
     * 查询用户某天伞下的卡数及进阶卡的收益总数
     *
     * @param selDate
     * @param userId
     * @return
     */
    Map<String, Object> getAdvanceShareSalary(@Param("selDate") Date selDate, @Param("userId") Integer userId);

    /**
     * 计算直推用户伞下签约卡收益 烧杀
     * @param userId
     * @param selDate
     * @param burnValue
     * @return
     */
    BigDecimal getTotalSum(@Param("userId") Integer userId, @Param("selDate") Date selDate, @Param("burnValue") double burnValue);

    /**
     * 查询收益列表
     * @return
     * @param params
     */
    List<InCome> list(@Param("map") Map<String, Object> params);

    /**
     * 昨日签约收益总额
     * @param yesterday
     * @return
     */
    BigDecimal yesterdaySignIncomeCount(Date yesterday);
}
