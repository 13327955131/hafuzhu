package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.entity.HfzUserCard;
import com.hoostec.hfz.entity.HfzUserCardRecord;
import com.hoostec.hfz.service.HfzUserCardRecordService;
import com.hoostec.hfz.service.HfzUserCardService;
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
@RequestMapping("hfz/userCard")
public class HfzUserCardController {




    @Autowired
    private HfzUserCardService hfzUserCardService;


    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('userCard:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUserCard obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzUserCard> list = hfzUserCardService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }




}
