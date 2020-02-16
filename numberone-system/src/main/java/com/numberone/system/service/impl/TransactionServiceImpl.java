package com.numberone.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.*;
import com.numberone.system.domain.Wallet;
import com.numberone.system.mapper.SysConfigMapper;
import com.numberone.system.mapper.TransactionMapper;
import com.numberone.system.mapper.WalletMapper;
import com.numberone.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {


    private final SysConfigMapper configMapper;
    private final TransactionMapper transactionMapper;
    private final WalletMapper walletMapper;
    private final Web3j web3j;



    @Autowired
    public TransactionServiceImpl(TransactionMapper transactionMapper,WalletMapper walletMapper,SysConfigMapper configMapper){
        this.transactionMapper = transactionMapper;
        this.walletMapper = walletMapper;
        this.configMapper = configMapper;
        SysConfig config = configMapper.checkConfigKeyUnique("INFURA_ADDRESS");
        this.web3j = Web3j.build(new HttpService(config.getConfigValue()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void settlementIncome(String userId, String usdtMoney, String mdcMoney) {
        EntityWrapper<Wallet> walletEntityWrapper = new EntityWrapper<>();
        walletEntityWrapper.eq("user_id",userId);
        List<Wallet> walletList = walletMapper.selectList(walletEntityWrapper);
        if(walletList.size() > 0){
            Wallet wallet = walletList.get(0);
            //usdt收益
            if(new Double(usdtMoney)>0){
                BigDecimal usdtIncome = new BigDecimal(usdtMoney);
                BigDecimal usdtBalance = wallet.getUstdBlance();
                wallet.setUstdBlance(usdtBalance.add(usdtIncome));
                Transaction usdtTransaction = new Transaction();
                usdtTransaction.setToAmount(usdtIncome);
                usdtTransaction.setToUserId(Integer.parseInt(userId));
                usdtTransaction.setToWalletAddress(wallet.getAddress());
                usdtTransaction.setToWalletType("0");
                usdtTransaction.setTransactionStatus("1");
                usdtTransaction.setTransactionType("6");
                usdtTransaction.setCreateTime(new Date());
                usdtTransaction.setFeeAmount(new BigDecimal(0));
                transactionMapper.insert(usdtTransaction);
            }
            if(new Double(mdcMoney)>0){
                //mdc收益
                BigDecimal mdcIncome = new BigDecimal(mdcMoney);
                BigDecimal mdcBalance = wallet.getUstdBlance();
                wallet.setMdcBlance(mdcBalance.add(mdcIncome));
                Transaction mdcTransaction = new Transaction();
                mdcTransaction.setToAmount(new BigDecimal(mdcMoney));
                mdcTransaction.setToUserId(Integer.parseInt(userId));
                mdcTransaction.setToWalletAddress(wallet.getAddress());
                mdcTransaction.setToWalletType("1");
                mdcTransaction.setTransactionStatus("1");
                mdcTransaction.setTransactionType("6");
                mdcTransaction.setCreateTime(new Date());
                mdcTransaction.setFeeAmount(new BigDecimal(0));
                transactionMapper.insert(mdcTransaction);
            }
            walletMapper.updateById(wallet);
        }
    }

    @Override
    public List<Map<String,Object>> getTransaction(Map<String,Object> params) {
        return transactionMapper.getTransaction(params);
    }

    @Override
    public List<TransactionVo> exportTransaction(Map<String, Object> params) {
        return transactionMapper.exportTransaction(params);
    }

    @Override
    public List<InvestCashOutVo> exportInvestCashOut(Map<String, Object> params) {
        return transactionMapper.exportInvestCashOut(params);
    }

    @Override
    public List<InvestVo> exportInvest(Map<String, Object> params) {
        return transactionMapper.exportInvest(params);
    }

    @Override
    public List<CashOutVo> exportCashOut(Map<String, Object> params) {
        return transactionMapper.exportCashOut(params);
    }

    @Override
    public List<Map<String,Object>> investCashOut(Map<String,Object> params) {
        return transactionMapper.getTransaction(params);
    }

    @Override
    public List<ContractVo> getContract(Map<String,Object> params) {
        return transactionMapper.getContract(params);
    }

    @Override
    public AjaxResult personHandleCashOut(Map<String, Object> params) throws InterruptedException, ExecutionException, CipherException, IOException {
        String ids = (String) params.get("ids");
        String type = (String) params.get("type");
        Transaction transaction = transactionMapper.selectById(ids);
        if("-1".equals(type)){
            transaction.setTransactionStatus("-2");
            transactionMapper.updateById(transaction);
            EntityWrapper<Wallet> walletEntityWrapper = new EntityWrapper<>();
            walletEntityWrapper.eq("user_id",transaction.getFromUserId());
            List<Wallet> walletList = walletMapper.selectList(walletEntityWrapper);
            Wallet wallet = walletList.get(0);
            BigDecimal usdt = wallet.getUstdBlance();
            wallet.setUstdBlance(usdt.add(transaction.getFromAmount()));
            walletMapper.updateById(wallet);
        }else{
            SysConfig walletPath = configMapper.checkConfigKeyUnique("WALLET_PATH");
            SysConfig walletAddress = configMapper.checkConfigKeyUnique("WALLET_ADDRESS");
            //String wallet_Path = "D://walletTrue//UTC--2020-02-07T13-31-22.32000000Z--eb04131fbe988d43c0f9c0d8a30ccc3636994dda.json";
            String wallet_Path = walletPath.getConfigValue();
            AjaxResult transactionHash = transfer("123456",transaction.getToAmount(),wallet_Path,walletAddress.getConfigValue(),transaction.getToWalletAddress(),"0");
            if("0".equals(transactionHash.get("code").toString())){
                transaction.setTransactionHash(transactionHash.get("msg").toString());
                transaction.setTransactionStatus("1");
                transactionMapper.updateById(transaction);
            }else{
                return transactionHash;
            }

        }
        return AjaxResult.success();
    }

    @Override
    public Map<String, Object> investCashOutSize(Map<String,Object> params) {
        Map<String,Object> map = new HashMap<>();
        params.put("transactionType","0");
        List<Map<String,Object>> invest =  transactionMapper.transactionAmountSum(params);
        if(invest.size() > 0){
            map.put("invest",invest.get(0).get("invest"));
        }else{
            map.put("invest",0);
        }
        params.put("transactionType","1");
        List<Map<String,Object>> cashOut =  transactionMapper.transactionAmountSum(params);
        if(cashOut.size() > 0){
            map.put("cashOut",cashOut.get(0).get("cashOut"));
        }else{
            map.put("cashOut",0);
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public AjaxResult transfer(String payPassword, BigDecimal trans, String fromPath, String fromAddress, String toAddress, String walletType) throws IOException, CipherException, ExecutionException, InterruptedException {



        //判断转出地址
        if(!toAddress.startsWith("0x") || toAddress.length() != 42){
            return AjaxResult.error("提现地址不存在");
        }
        Credentials credentials = WalletUtils.loadCredentials(payPassword, fromPath);
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version=" + clientVersion);
        String transactionHash;

        BigDecimal eth;
        BigDecimal fee = new BigDecimal(0);
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        Address transferAddress = new Address(toAddress);
        String contractAddress = "";
        if("0".equals(walletType)){
            eth = new BigDecimal("1000000");

            contractAddress = "0xdac17f958d2ee523a2206206994597c13d831ec7";
        }else if("1".equals(walletType)){
            eth = new BigDecimal("1000000000000000000");
            contractAddress = "0x98ED701E2dc5A362da6AB791fA8d921E6C1c14e1";
        }else{
            return AjaxResult.error("审核失败");
        }
        Uint256 value = new Uint256(new BigInteger(trans.multiply(eth).stripTrailingZeros().toPlainString()));
        List<Type> parametersList = new ArrayList<>();
        parametersList.add(transferAddress);
        parametersList.add(value);
        List<TypeReference<?>> outList = new ArrayList<>();
        Function transfer = new Function("transfer", parametersList, outList);
        String encodedFunction = FunctionEncoder.encode(transfer);
        BigInteger gasPrice = Convert.toWei(new BigDecimal("18"), Convert.Unit.GWEI).toBigInteger();

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice,
                new BigInteger("100000"),contractAddress, encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        transactionHash = ethSendTransaction.getTransactionHash();
        System.out.println(transactionHash);
        return  AjaxResult.success(transactionHash);
    }
}
