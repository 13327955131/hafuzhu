package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.entity.HfzUserBalance;
import com.hoostec.hfz.service.HfzUserBalanceService;
import com.hoostec.hfz.service.HfzUserService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户  积分余额小金库
 */
@RestController
@RequestMapping("hfz/userBalance")
public class HfzUserBalanceController {

    @Autowired
    private HfzUserService hfzUserService;

    @Autowired
    private HfzUserBalanceService hfzUserBalanceService;


    /**
     * 金库列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('balance:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserBalance hfzUserBalance, PageUtil page) {
        hfzUserBalance.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzUserBalance> list = hfzUserBalanceService.selectAll(hfzUserBalance, page);




        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     * 用户更新
     *
     * @return
     */
    @MyLog("小金库更新")
    @PreAuthorize("hasAuthority('balance:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzUserBalance user) {
        // 更新用户表
        hfzUserBalanceService.update(user);

        return ResultDataUtil.isOk(null);
    }






















}
