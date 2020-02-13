package com.numberone.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.numberone.common.page.PageDomain;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.page.TableSupport;
import com.numberone.common.utils.StringUtils;
import com.numberone.system.domain.FeedBack;
import com.numberone.system.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/system/feedback")
@Api("意见反馈")
public class FeedBackController {

    private String prefix = "system/feedback";
    @Autowired
    private FeedBackService feedBackService;

    @GetMapping()
    public String operlog() {
        return prefix + "/feedback";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("/反馈列表")
    public TableDataInfo list(String createBy, String createTime) {
        if (createTime==null){
            createTime ="";
        }
        startPage();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         ParsePosition pos = new ParsePosition(0);
         Date strtodate = formatter.parse(createTime, pos);
        List<FeedBack> list = feedBackService.list(createBy,strtodate);
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
    protected TableDataInfo getDataTable(List <?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
