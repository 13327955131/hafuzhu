package com.hoostec.hfz.controller.back;

import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsMenu;
import com.hoostec.hfz.service.CmsMenuService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单管理
 *
 * @author loo
 * @version 1.0.0
 */
@RestController
@RequestMapping("cms/menu")
public class CmsMenuController {
    @Autowired
    private CmsMenuService cmsMenuService;

    /**
     * 菜单列表
     *
     * @param plcMenu
     * @return ResultDataUtil
     */
    @PreAuthorize("hasAuthority('menu:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(CmsMenu plcMenu) {
        plcMenu.setDelStatus(Constant.DEL_STATUS_ONE);
        List<CmsMenu> list = cmsMenuService.selectAll(plcMenu);
        return ResultDataUtil.isOk(list);
    }

    /**
     * 菜单删除
     *
     * @param request
     * @return ResultDataUtil
     */
    @MyLog("菜单删除")
    @PreAuthorize("hasAuthority('menu:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(cmsMenuService.deleteAll(ids));
    }

    /**
     * 菜单新增
     *
     * @param plcMenu
     * @return ResultDataUtil
     */
    @MyLog("菜单新增")
    @PreAuthorize("hasAuthority('menu:add')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDataUtil menuAdd(CmsMenu plcMenu) {
        return ResultDataUtil.isOk(cmsMenuService.insert(plcMenu));
    }

    /**
     * 菜单更新
     *
     * @param plcMenu
     * @return ResultDataUtil
     */
    @MyLog("菜单更新")
    @PreAuthorize("hasAuthority('menu:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDataUtil menuUpdate(CmsMenu plcMenu) {
        return ResultDataUtil.isOk(cmsMenuService.update(plcMenu));
    }
}
