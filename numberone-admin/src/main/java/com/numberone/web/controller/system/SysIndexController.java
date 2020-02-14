package com.numberone.web.controller.system;

import java.util.List;

import com.numberone.common.base.AjaxResult;
import com.numberone.system.service.ISysIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.numberone.common.config.Global;
import com.numberone.system.domain.SysMenu;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysMenuService;
import com.numberone.framework.web.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页 业务处理
 * 
 * @author numberone
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysIndexService sysIndexService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", Global.getVersion());
        return "main";
    }


    @ResponseBody
    @PostMapping("/mainInfo")
    public AjaxResult mainInfo()
    {
        return AjaxResult.success().put("data",sysIndexService.mainInfo());
    }
}
