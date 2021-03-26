package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.HfzUserAddress;
import com.hoostec.hfz.service.HfzUserAddressService;
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
@RequestMapping("hfz/userAddress")
public class HfzUserAddressController {


    @Autowired
    private HfzUserAddressService hfzUserAddressService;



    /**
     * 用户地址列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('userAddress:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserAddress obj, PageUtil page) {
        PageInfo<HfzUserAddress> list = hfzUserAddressService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }



}
