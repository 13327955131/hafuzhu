package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzGoodsOrder;
import com.hoostec.hfz.service.HfzGoodsOrderService;
import com.hoostec.hfz.service.HfzGoodsTypeService;
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
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/goodsOrder")
public class HfzGoodsOrderController {


    @Autowired
    private HfzGoodsTypeService hfzGoodsTypeService;

    @Autowired
    private HfzGoodsOrderService hfzGoodsOrderService;


    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('goodsOrder:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzGoodsOrder obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzGoodsOrder> list = hfzGoodsOrderService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('goodsOrder:list')")
    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public ResultDataUtil selectAllUserGood(HfzGoodsOrder obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        obj.setOrderStatus(1);
        List<HfzGoodsOrder> list = hfzGoodsOrderService.selectAllUserGood(obj);
        return ResultDataUtil.isOk(list);
    }


    /**
     * 修改订单
     * @param obj
     * @return
     */
    @MyLog("修改订单")
    @PreAuthorize("hasAuthority('goodsOrder:list')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzGoodsOrder obj) {
        // 更新用户表
        hfzGoodsOrderService.update(obj);

        return ResultDataUtil.isOk(null);
    }



}
