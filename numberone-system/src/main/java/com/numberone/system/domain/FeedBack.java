package com.numberone.system.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@TableName("mdc_feedback")
@Data
@Builder
public class FeedBack {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("message")
    private String message;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    public FeedBack() {
    }

    public FeedBack(Integer id, String message, String createBy, Date createTime) {
        this.id = id;
        this.message = message;
        this.createBy = createBy;
        this.createTime = createTime;
    }
}