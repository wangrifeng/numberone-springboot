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
import com.numberone.system.domain.UserTmpLevel;
import com.numberone.system.service.InComeService;
import com.numberone.system.service.UserTmpLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/userTmpLevel")
@Api("收益管理")
public class UserTmpLevelController {

    @Autowired
    private UserTmpLevelService userTmpLevelService;

    private String prefix = "system/usertmplevel";

    @GetMapping()
    public String html(@RequestParam Map<String,Object> params, ModelMap mmap)
    {
        return prefix + "/usertmplevel";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/用户临时等级")
    public TableDataInfo list(@RequestParam Map<String,Object> params) {
        startPage();
        List<UserTmpLevel> list = userTmpLevelService.list(params);
        return getDataTable(list);
    }

    /**
     * 新增用户临时等级
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        return prefix + "/add";
    }

    /**
     * 新增用户临时等级
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult doAdd(@RequestParam String loginName, @RequestParam Integer level ,@RequestParam Integer number)
    {
        return userTmpLevelService.add(loginName,level,number);
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
