package com.numberone.system.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@TableName("mdc_transaction")
public class Transaction extends Model<Transaction> {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    @TableId(value = "transaction_id", type = IdType.AUTO)
    private Integer transactionId;
    /**
     * 支出用户id
     */
    @TableField("from_user_id")
    private Integer fromUserId;
    /**
     * 支出钱包id
     */
    @TableField("from_user_address")
    private String fromWalletAddress;
    /**
     * 收取用户id
     */
    @TableField("to_user_id")
    private Integer toUserId;
    /**
     * 收取钱包id
     */
    @TableField("to_wallet_address")
    private String toWalletAddress;
    /**
     * 交易金额
     */
    @TableField("from_amount")
    private BigDecimal fromAmount;
    @TableField("from_wallet_type")
    private String fromWalletType;
    @TableField("to_amount")
    private BigDecimal toAmount;
    @TableField("to_wallet_type")
    private String toWalletType;
    @TableField("fee_amount")
    private BigDecimal feeAmount;
    /**
     * 交易类型（0-转账）
     */
    @TableField("transaction_type")
    private String transactionType;
    /**
     * 交易时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 交易说明
     */
    @TableField("remark")
    private String remark;
    @TableField("transaction_status")
    private String transactionStatus;
    @TableField("transaction_hash")
    private String transactionHash;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromWalletAddress() {
        return fromWalletAddress;
    }

    public void setFromWalletAddress(String fromWalletAddress) {
        this.fromWalletAddress = fromWalletAddress;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getToWalletAddress() {
        return toWalletAddress;
    }

    public void setToWalletAddress(String toWalletAddress) {
        this.toWalletAddress = toWalletAddress;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getFromWalletType() {
        return fromWalletType;
    }

    public void setFromWalletType(String fromWalletType) {
        this.fromWalletType = fromWalletType;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public String getToWalletType() {
        return toWalletType;
    }

    public void setToWalletType(String toWalletType) {
        this.toWalletType = toWalletType;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    @Override
    protected Serializable pkVal() {
        return this.transactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", transactionId=" + transactionId +
                ", fromUserId=" + fromUserId +
                ", fromWalletAddress=" + fromWalletAddress +
                ", toUserId=" + toUserId +
                ", toWalletAddress=" + toWalletAddress +
                ", transactionType=" + transactionType +
                ", createTime=" + createTime +
                ", remark=" + remark +
                "}";
    }
}
