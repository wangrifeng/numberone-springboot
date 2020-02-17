package com.numberone.system.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.numberone.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
public class WalletVo{

    private static final long serialVersionUID = 1L;


    @Excel(name = "钱包id")
    private Integer wallet_id;

    /**
     * 用户id
     */

    @Excel(name="用户账号")
    private String login_name;
    @Excel(name = "钱包地址")
    private String address;
    /**
     * 私有密钥
     */
    @Excel(name = "usdt余额")
    private BigDecimal ustd_blance;
    @Excel(name = "mdc余额")
    private BigDecimal mdc_blance;

    @Excel(name="创建时间")
    private Date create_time;

    public Integer getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(Integer wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getUstd_blance() {
        return ustd_blance;
    }

    public void setUstd_blance(BigDecimal ustd_blance) {
        this.ustd_blance = ustd_blance;
    }

    public BigDecimal getMdc_blance() {
        return mdc_blance;
    }

    public void setMdc_blance(BigDecimal mdc_blance) {
        this.mdc_blance = mdc_blance;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
