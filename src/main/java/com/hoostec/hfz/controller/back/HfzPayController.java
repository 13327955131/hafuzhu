package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.HfzPayDay;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.service.HfzPayDayService;
import com.hoostec.hfz.service.HfzUserService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 每日支出
 */
@RestController
@RequestMapping("hfz/pay")
public class HfzPayController {


    @Autowired
    private HfzPayDayService hfzPayDayService;


    /**
     * 查询全部
     *
     * @return
     */
    @PreAuthorize("hasAuthority('pay:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzPayDay hfzUser, PageUtil page) {
        hfzUser.setDelStatus(Constant.DEL_STATUS_ONE);


        PageInfo<HfzPayDay> list = hfzPayDayService.selectAll(hfzUser, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }







}
