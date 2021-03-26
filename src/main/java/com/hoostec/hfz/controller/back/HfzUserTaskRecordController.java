package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzFeedback;
import com.hoostec.hfz.entity.HfzUserTaskRecord;
import com.hoostec.hfz.service.HfzFeedbackService;
import com.hoostec.hfz.service.HfzUserTaskRecordService;
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
@RequestMapping("hfz/userTaskRecord")
public class HfzUserTaskRecordController {



    @Autowired
    private HfzUserTaskRecordService hfzUserTaskRecordService;



    /**
     * 任务记录列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('userTaskRecord:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserTaskRecord obj, PageUtil page) {
        if("".equals(obj.getDayTime())){
            obj.setDayTime(null);
        }

        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzUserTaskRecord> list = hfzUserTaskRecordService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }







}
