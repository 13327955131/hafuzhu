package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzGoods;
import com.hoostec.hfz.entity.HfzSign;
import com.hoostec.hfz.service.HfzGoodsService;
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
@RequestMapping("hfz/sign")
public class HfzSignController {




    @Autowired
    private HfzGoodsService hfzGoodsService;
    @Autowired
    private HfzSignService hfzSignService;


    /**
     * 签到列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sign:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil goodList(HfzSign obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzSign> list = hfzSignService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }




}
