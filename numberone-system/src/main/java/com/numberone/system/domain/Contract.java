package com.numberone.system.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.numberone.common.utils.SpringContextHolder;
import com.numberone.system.service.ISysConfigService;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("sys_contract")
@Data
public class Contract {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("type")
    private Integer type;

    @TableField("level")
    private Integer level;

    @TableField("name")
    private String name;

    @TableField("price")
    private BigDecimal price;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("unit")
    private String unit;

    @TableField("introduce")
    private String introduce;

    @TableField("audio_path")
    private String audioPath;

    @TableField("income_rate")
    private BigDecimal incomeRate;

    @TableField("out_rate")
    private BigDecimal outRate;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    public BigDecimal getAmount() {
        ISysConfigService configService = SpringContextHolder.applicationContext.getBean(ISysConfigService.class);
        if(this.type == 1 && this.level == 1){
            //屌丝
            return new BigDecimal(configService.selectConfigByKey("diaosi_amount"));
        }else if(this.type == 1 && this.level == 2){
            //网红
            return new BigDecimal(configService.selectConfigByKey("wanghong_amount"));
        }else if(this.type == 1 && this.level == 3){
            //明星
            return new BigDecimal(configService.selectConfigByKey("mingxing_amount"));
        }else if(this.type == 1 && this.level == 4){
            //大咖
            return  new BigDecimal(configService.selectConfigByKey("daka_amount"));
        }else if(this.type == 2){
            //进阶卡
            return new BigDecimal(configService.selectConfigByKey("advance_card_price"));
        }
        return new BigDecimal(0);
    }

    public BigDecimal getIncomeRate() {
        ISysConfigService configService = SpringContextHolder.applicationContext.getBean(ISysConfigService.class);
        if(this.type == 1 && this.level == 1){
            //屌丝
            return new BigDecimal(configService.selectConfigByKey("diaosi_rate"));
        }else if(this.type == 1 && this.level == 2){
            //网红
            return new BigDecimal(configService.selectConfigByKey("wanghong_rate"));
        }else if(this.type == 1 && this.level == 3){
            //明星
            return new BigDecimal(configService.selectConfigByKey("mingxing_rate"));
        }else if(this.type == 1 && this.level == 4){
            //大咖
            return  new BigDecimal(configService.selectConfigByKey("daka_rate"));
        }else if(this.type == 2){
            //进阶卡
            return new BigDecimal(configService.selectConfigByKey("advance_card_income_rate"));
        }
        return new BigDecimal(0);
    }
}
