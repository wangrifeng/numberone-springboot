package com.numberone.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.numberone.common.page.PageDomain;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.page.TableSupport;
import com.numberone.common.utils.StringUtils;
import com.numberone.system.domain.Transaction;
import com.numberone.system.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/invest")
@Api("充值记录")
public class InvestController {

    @Autowired
    private TransactionService transactionService;

    private String prefix = "system/invest";

    @GetMapping()
    public String operlog()
    {
        return prefix + "/invest";
    }

    @GetMapping("/cashOutHtml")
    public String cashOutHtml()
    {
        return  "system/cashOut/cashOut";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/充值记录列表")
    public TableDataInfo list() {
        startPage();
        Map<String,Object> params = new HashMap<>();
        params.put("transactionType","0");
        List<Map<String,Object>> list = transactionService.getTransaction(params);
        return getDataTable(list);
    }

    @PostMapping("/cashOut")
    @ResponseBody
    @ApiOperation("/提现记录列表")
    public TableDataInfo cashOut() {
        startPage();
        Map<String,Object> params = new HashMap<>();
        params.put("transactionType","1");
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
