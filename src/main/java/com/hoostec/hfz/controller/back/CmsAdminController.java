package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.service.CmsAdminService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 *
 * @author loo
 * @version 1.0.0
 */
@RestController
@RequestMapping("cms/admin")
public class CmsAdminController {
    @Autowired
    private CmsAdminService plcUserService;

    /**
     * 用户列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('admin:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(CmsAdmin user, PageUtil page) {
        user.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<CmsAdmin> list = plcUserService.selectAll(user, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     * 用户删除
     *
     * @param request
     * @return
     */
    @MyLog("用户删除")
    @PreAuthorize("hasAuthority('admin:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(plcUserService.deleteAll(ids));
    }


    /**
     * 用户新增
     *
     * @return
     */
    @MyLog("用户新增")
    @PreAuthorize("hasAuthority('admin:add')")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDataUtil menuAdd(CmsAdmin user) {
        CmsAdmin admin = plcUserService.selectUserByName(user.getUsername());
        if (admin != null) {
            return ResultDataUtil.isError(Constant.ERROR_CODE_1003, "账号已存在！");
        } else {

            // 插入用户表
            plcUserService.insert(user);

            return ResultDataUtil.isOk(null);
        }
    }

    /**
     * 用户更新
     *
     * @return
     */
    @MyLog("用户更新")
    @PreAuthorize("hasAuthority('admin:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(CmsAdmin user) {
        // 更新用户表
        plcUserService.update(user);

        return ResultDataUtil.isOk(null);
    }
}
