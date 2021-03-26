package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzNotice;
import com.hoostec.hfz.entity.HfzTask;
import com.hoostec.hfz.service.HfzNoticeService;
import com.hoostec.hfz.service.HfzTaskService;
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
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/task")
public class HfzTaskController {





    @Autowired
    private HfzNoticeService hfzNoticeService;

    @Autowired
    private HfzTaskService hfzTaskService;


    /**
     * 公告列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('task:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil goodList(HfzTask obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzTask> list = hfzTaskService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     *  新增任务
     *
     * @return
     */
    @MyLog("新增任务")
    @PreAuthorize("hasAnyAuthority('task:list')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil goodsTypeAdd(HfzTask obj) {
        return ResultDataUtil.isOk(hfzTaskService.insert(obj));
    }
    /**
     *  删除公告
     *
     * @return
     */
    @MyLog("删除任务")
    @PreAuthorize("hasAuthority('task:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzTaskService.deleteAll(ids));
    }

    @MyLog("修改任务")
    @PreAuthorize("hasAuthority('task:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzTask obj) {
        hfzTaskService.update(obj);
        return ResultDataUtil.isOk(null);
    }


}
