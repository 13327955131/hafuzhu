package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.HfzProfitPickRecord;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.entity.HfzUserIntegralRecord;
import com.hoostec.hfz.service.HfzProfitPickRecordService;
import com.hoostec.hfz.service.HfzUserIntegralRecordService;
import com.hoostec.hfz.service.HfzUserService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 财务统计------收益提现记录
 */
@RestController
@RequestMapping("hfz/userIntegralRecord")
public class HfzUserIntegralRecordController {
    @Autowired
    private HfzUserIntegralRecordService hfzUserIntegralRecordService;

    @Autowired
    private HfzUserService hfzUserService;


    /**
     * 收益提现列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('userIntegralRecord:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserIntegralRecord hfzProfitRecord, PageUtil page) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzUserIntegralRecord> list = hfzUserIntegralRecordService.selectAll(hfzProfitRecord, page);

        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 收益提现全部列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('userIntegralRecord:list','goodsOrder:list')")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public ResultDataUtil listAll(HfzUserIntegralRecord hfzProfitRecord) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzUserIntegralRecord> list = hfzUserIntegralRecordService.selectAll(hfzProfitRecord);
        return ResultDataUtil.isOk(list);
    }





}
