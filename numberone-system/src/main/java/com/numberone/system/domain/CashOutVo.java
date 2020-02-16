package com.numberone.system.domain;

import com.numberone.common.annotation.Excel;

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
public class CashOutVo {

    private static final long serialVersionUID = 1L;


    @Excel(name = "记录id")
    private Integer transaction_id;
    @Excel(name="交易时间")
    private Date create_time;

    /**
     * 用户id
     */

    @Excel(name="用户名称")
    private String fromUserName;
    @Excel(name = "提现钱包地址")
    private String from_wallet_address;

    @Excel(name = "提现数量")
    private BigDecimal from_amount;
    /**
     * 私有密钥
     */

    @Excel(name = "提现币种")
    private String walletType;

    @Excel(name = "手续费")
    private BigDecimal fee_amount;


    @Excel(name = "到账钱包地址")
    private String to_wallet_address;

    @Excel(name = "状态")
    private String transactionStatus;

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }


    public String getTo_wallet_address() {
        return to_wallet_address;
    }

    public void setTo_wallet_address(String to_wallet_address) {
        this.to_wallet_address = to_wallet_address;
    }



    public String getFrom_wallet_type() {
        return walletType;
    }

    public void setFrom_wallet_type(String from_wallet_type) {
        this.walletType = from_wallet_type;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFrom_wallet_address() {
        return from_wallet_address;
    }

    public void setFrom_wallet_address(String from_wallet_address) {
        this.from_wallet_address = from_wallet_address;
    }

    public BigDecimal getFrom_amount() {
        return from_amount;
    }

    public void setFrom_amount(BigDecimal from_amount) {
        this.from_amount = from_amount;
    }

    public BigDecimal getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(BigDecimal fee_amount) {
        this.fee_amount = fee_amount;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
}
