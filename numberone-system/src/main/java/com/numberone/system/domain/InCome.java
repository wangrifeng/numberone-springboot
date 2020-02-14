package com.numberone.system.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.numberone.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("mdc_income")
@Data
public class InCome {

    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "id")
    private Integer id;


    @TableField("user_id")
    private Integer userId;

    @TableField("salary")
    @Excel(name = "最终收益")
    private BigDecimal salary;

    @TableField("contract_salary")
    @Excel(name = "静态收益")
    private BigDecimal contractSalary;

    @TableField("share_salary")
    @Excel(name = "分享收益")
    private BigDecimal shareSalary;

    @TableField("type")
    private Integer type;

    @TableField("unit")
    private String unit;

    @TableField("number")
    private Integer number;

    @TableField("contract_id")
    private Integer contractId;

    @TableField("remark")
    private String remark;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("rate")
    private BigDecimal rate;

    @TableField("sel_date")
    @Excel(name = "时间")
    private Date selDate;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("is_cal_msalary")
    private Integer isCalMsalary;

    @TableField("manage_salary")
    @Excel(name = "管理收益")
    private BigDecimal manageSalary;

    @TableField("same_level_salary")
    @Excel(name = "平级收益")
    private BigDecimal sameLevelSalary;


    @TableField(exist = false)
    @Excel(name = "收益人")
    private String userName;

    @TableField(exist = false)
    private String contractName;

    @TableField(exist = false)
    @Excel(name = "合约类型", readConverterExp = "1=屌丝,2=网红,3=明星,4=大咖")
    private Integer level;

    public InCome() {
    }

    public InCome(Integer userId, String unit, Integer contractId, int type, String remark, BigDecimal amount, BigDecimal incomeRate,Date selDate, Date createTime) {
        this.userId = userId;
        this.unit = unit;
        this.contractId = contractId;
        this.type = type;
        this.remark = remark;
        this.amount = amount;
        this.rate = incomeRate;
        this.selDate = selDate;
        this.createTime = createTime;
    }
}
