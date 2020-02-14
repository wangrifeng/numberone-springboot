package com.numberone.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.page.PageDomain;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.page.TableSupport;
import com.numberone.common.utils.StringUtils;
import com.numberone.system.service.TransactionService;
import com.numberone.system.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        List<Map<String,Object>> list = walletService.getWallet(params);
        return getDataTable(list);
    }

    @PostMapping("/transaction")
    @ResponseBody
    @ApiOperation("/交易记录列表")
    public TableDataInfo transaction(@RequestParam Map<String,Object> params) {
        startPage();
        List<Map<String,Object>> list = transactionService.getTransaction(params);
        return getDataTable(list);
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
