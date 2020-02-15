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
public class ContractVo {

    private static final long serialVersionUID = 1L;


    @Excel(name = "id")
    private Integer transaction_id;

    /**
     * 用户id
     */
    @Excel(name = "合约ID")
    private String contract_id;
    @Excel(name="用户账号")
    private String fromUserName;
    @Excel(name = "合约类型")
    private String contractName;
    /**
     * 私有密钥
     */
    @Excel(name = "数量")
    private BigDecimal from_amount;
    @Excel(name = "类型")
    private String contractType;

    @Excel(name="交易时间")
    private Date create_time;

    @Excel(name = "状态")
    private String transactionStatus;

    @Excel(name = "备注")
    private String transaction_type;

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public BigDecimal getFrom_amount() {
        return from_amount;
    }

    public void setFrom_amount(BigDecimal from_amount) {
        this.from_amount = from_amount;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
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
}
