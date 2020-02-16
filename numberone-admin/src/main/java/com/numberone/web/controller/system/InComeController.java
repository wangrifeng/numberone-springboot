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
import com.numberone.system.domain.InCome;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.InComeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/income")
@Api("收益管理")
public class InComeController {

    @Autowired
    private InComeService inComeService;

    private String prefix = "system/income";

    @GetMapping()
    public String operlog(@RequestParam Map<String,Object> params, ModelMap mmap)
    {
        if(params.get("userId") != null){
            mmap.put("income",params);
        }
        return prefix + "/income";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/收益列表")
    public TableDataInfo list(@RequestParam Map<String,Object> params) {
        startPage();
        List<InCome> list = inComeService.list(params);
        return getDataTable(list);
    }


    @Log(title = "收益导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(@RequestParam Map<String,Object> params)
    {
        List<InCome> list = inComeService.list(params);
        ExcelUtil<InCome> util = new ExcelUtil<InCome>(InCome.class);
        return util.exportExcel(list, "收益列表");
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
