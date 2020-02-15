package com.numberone.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.PageDomain;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.page.TableSupport;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.*;
import com.numberone.system.service.TransactionService;
import com.numberone.system.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/wallet")
@Api("钱包信息")
public class WalletController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;
    private String prefix = "system/wallet";

    @GetMapping()
    public String operlog()
    {
        return prefix + "/wallet";
    }

    @GetMapping("/walletInfo")
    @ApiOperation("/钱包信息")
    public String walletInfo(@RequestParam Map<String,Object> params,ModelMap mmap) {
        mmap.put("wallet",params);
        return prefix+"/walletInfo";
    }

    @GetMapping("/handlerBalance")
    @ApiOperation("/人工改动余额")
    public String handlerBalance(@RequestParam Map<String,Object> params,ModelMap mmap) {
        mmap.put("wallet",params);
        return prefix+"/handlerBalance";
    }

    @PostMapping("/balanceChange")
    @ResponseBody
    @ApiOperation("/修改余额")
    public AjaxResult balanceChange(@RequestParam Map<String,Object> params) {
        try {
            return walletService.changeBalance(params);
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/钱包信息")
    public TableDataInfo list(@RequestParam Map<String,Object> params) {
        startPage();
        List<WalletVo> list = walletService.getWallet(params);
        return getDataTable(list);
    }

    @Log(title = "钱包信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportWallet")
    @ResponseBody
    public AjaxResult exportWallet(@RequestParam Map<String,Object> params)
    {
        List<WalletVo> list = walletService.getWallet(params);
        ExcelUtil<WalletVo> util = new ExcelUtil<>(WalletVo.class);
        return util.exportExcel(list, "钱包信息");
    }


    @PostMapping("/transaction")
    @ResponseBody
    @ApiOperation("/交易记录列表")
    public TableDataInfo transaction(@RequestParam Map<String,Object> params) {
        startPage();
        List<Map<String,Object>> list = transactionService.getTransaction(params);
        return getDataTable(list);
    }

    @Log(title = "转账信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportTransaction")
    @ResponseBody
    public AjaxResult exportTransaction(@RequestParam Map<String,Object> params)
    {
        List<TransactionVo> list = transactionService.exportTransaction(params);
        ExcelUtil<TransactionVo> util = new ExcelUtil<>(TransactionVo.class);
        return util.exportExcel(list, "转账信息");
    }

    @PostMapping("/investCashOut")
    @ResponseBody
    @ApiOperation("/充值提现列表")
    public TableDataInfo investCashOut(@RequestParam Map<String,Object> params) {
        startPage();
        List<Map<String,Object>> list = transactionService.investCashOut(params);
        return getDataTable(list);
    }

    @Log(title = "充值提现信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportInvestCashOut")
    @ResponseBody
    public AjaxResult exportInvestCashOut(@RequestParam Map<String,Object> params)
    {
        List<InvestCashOutVo> list = transactionService.exportInvestCashOut(params);
        ExcelUtil<InvestCashOutVo> util = new ExcelUtil<>(InvestCashOutVo.class);
        return util.exportExcel(list, "充值提现信息");
    }

    @PostMapping("/getContract")
    @ResponseBody
    @ApiOperation("/合约列表")
    public TableDataInfo getContract(@RequestParam Map<String,Object> params) {
        startPage();
        List<ContractVo> list = transactionService.getContract(params);
        return getDataTable(list);
    }

    @Log(title = "合约信息", businessType = BusinessType.EXPORT)
    @PostMapping("/exportContract")
    @ResponseBody
    public AjaxResult exportContract(@RequestParam Map<String,Object> params)
    {
        List<ContractVo> list = transactionService.getContract(params);
        ExcelUtil<ContractVo> util = new ExcelUtil<>(ContractVo.class);
        return util.exportExcel(list, "充值提现信息");
    }


    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }


    /**
     * 响应请求分页数据
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

}
