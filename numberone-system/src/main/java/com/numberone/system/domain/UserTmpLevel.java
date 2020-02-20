package com.numberone.system.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@TableName("mdc_user_tmp_level")
@Data
public class UserTmpLevel {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    @TableField(value = "user_id")
    private Integer userId ;
    @TableField(value = "level")
    private Integer level ;
    @TableField(value = "out_date")
    private Date outDate ;
    @TableField(value = "create_by")
    private String createBy;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_by")
    private String updateBy;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableLogic(value = "del_flag")
    private Integer delFlag;
    @TableField(exist = false)
    private String loginName;
    @TableField(exist = false)
    private Integer rawLevel;
}
