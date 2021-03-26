package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.CmsLogHandle;
import com.hoostec.hfz.entity.CmsLogLogin;
import com.hoostec.hfz.service.CmsLogHandleService;
import com.hoostec.hfz.service.CmsLogLoginService;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cms/log")
public class CmsLogController {

    @Autowired
    private CmsLogHandleService cmsLogHandleService;
    @Autowired
    private CmsLogLoginService cmsLogLoginService;

    /**
     * 操作日志
     *
     * @param page
     * @return ResultDataUtil
     */
    @PreAuthorize("hasAuthority('log:operate:list')")
    @RequestMapping("/handle/list")
    public ResultDataUtil logHandle(PageUtil page) {

        PageInfo<CmsLogHandle> logList = cmsLogHandleService.selectAll(page);

        return ResultDataUtil.isOkJsonList(logList.getTotal(), logList.getList());
    }

    /**
     * 登陆日志
     *
     * @param page
     * @return ResultDataUtil
     */
    @RequestMapping("/login/list")
    @PreAuthorize("hasAuthority('log:login:list')")
    public ResultDataUtil logLogin(PageUtil page) {

        PageInfo<CmsLogLogin> logList = cmsLogLoginService.selectAll(page);

        return ResultDataUtil.isOkJsonList(logList.getTotal(), logList.getList());
    }
}
