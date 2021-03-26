package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.HfzProfitRecord;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.service.HfzProfitRecordService;
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
 * 用户  积分余额小金库
 */
@RestController
@RequestMapping("hfz/record")
public class HfzProfitRecordController {
    @Autowired
    private HfzProfitRecordService hfzProfitRecordService;
    @Autowired
    private HfzUserService hfzUserService;


    /**
     * 收益列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('record:list','profitPickRecord:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzProfitRecord hfzProfitRecord, PageUtil page) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzProfitRecord> list = hfzProfitRecordService.selectAll(hfzProfitRecord, page);

        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 全部收益
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('profitPickRecord:list')")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public ResultDataUtil nuList(HfzProfitRecord hfzProfitRecord) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzProfitRecord> list = hfzProfitRecordService.selectAll(hfzProfitRecord);
        return ResultDataUtil.isOk(list);
    }


    /**
     * 收益列表+提现列表 组合成新表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('use:list')")
    @RequestMapping(value = "/selectProfitRecord", method = RequestMethod.GET)
    public ResultDataUtil selectProfitRecord(HfzProfitRecord hfzProfitRecord, PageUtil page) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzProfitRecord> list = hfzProfitRecordService.selectProfitRecord(hfzProfitRecord, page);

        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
}
