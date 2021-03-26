package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzLuckDrawRecord;
import com.hoostec.hfz.entity.HfzSign;
import com.hoostec.hfz.service.HfzGoodsService;
import com.hoostec.hfz.service.HfzLuckDrawRecordService;
import com.hoostec.hfz.service.HfzSignService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/luckDrawRecord")
public class HfzluckDrawRecordController {




    @Autowired
    private HfzLuckDrawRecordService hfzLuckDrawRecordService;

    @Autowired
    private HfzSignService hfzSignService;


    /**
     * 签到列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('luckDrawRecord:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil goodList(HfzLuckDrawRecord obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzLuckDrawRecord> list = hfzLuckDrawRecordService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }




}
