package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzCardType;
import com.hoostec.hfz.entity.HfzUserCardRecord;
import com.hoostec.hfz.service.HfzCardTypeService;
import com.hoostec.hfz.service.HfzUserCardRecordService;
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
 *   平台用户福卡记录
 */
@RestController
@RequestMapping("hfz/userCardRecord")
public class HfzUserCardRecordController {




    @Autowired
    private HfzUserCardRecordService hfzUserCardRecordService;


    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('userCardRecord:list','userCard:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserCardRecord obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzUserCardRecord> list = hfzUserCardRecordService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }



}
