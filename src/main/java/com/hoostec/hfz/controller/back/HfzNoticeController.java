package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzLuckDrawRecord;
import com.hoostec.hfz.entity.HfzNotice;
import com.hoostec.hfz.service.HfzLuckDrawRecordService;
import com.hoostec.hfz.service.HfzNoticeService;
import com.hoostec.hfz.service.HfzSignService;
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
@RequestMapping("hfz/notice")
public class HfzNoticeController {





    @Autowired
    private HfzNoticeService hfzNoticeService;


    /**
     * 公告列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('notice:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil goodList(HfzNotice obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzNotice> list = hfzNoticeService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     *  新增公告
     *
     * @return
     */
    @MyLog("新增公告")
    @PreAuthorize("hasAnyAuthority('notice:list')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil goodsTypeAdd(HfzNotice obj) {


        return ResultDataUtil.isOk(hfzNoticeService.insert(obj));
    }
    /**
     *  删除公告
     *
     * @return
     */
    @MyLog("删除公告")
    @PreAuthorize("hasAuthority('notice:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzNoticeService.deleteAll(ids));
    }

    @MyLog("修改公告")
    @PreAuthorize("hasAuthority('notice:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzNotice obj) {
        hfzNoticeService.update(obj);
        return ResultDataUtil.isOk(null);
    }


}
