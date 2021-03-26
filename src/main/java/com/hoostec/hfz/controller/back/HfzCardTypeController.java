package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzCardType;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.service.HfzCardTypeService;
import com.hoostec.hfz.service.HfzGoodsTypeService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/cardType")
public class HfzCardTypeController {




    @Autowired
    private HfzCardTypeService hfzCardTypeService;


    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('cardType:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzCardType obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzCardType> list = hfzCardTypeService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }




    /**
     *  新增类型
     *
     * @return
     */
    @MyLog("新增类型")
    @PreAuthorize("hasAnyAuthority('cardType:list')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil goodsTypeAdd(HfzCardType obj) {
        return ResultDataUtil.isOk(hfzCardTypeService.insert(obj));
    }
    /**
     *  删除类型
     *
     * @return
     */
    @MyLog("类型删除")
    @PreAuthorize("hasAuthority('cardType:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzCardTypeService.deleteAll(ids));
    }

    @MyLog("修改类型")
    @PreAuthorize("hasAuthority('cardType:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzCardType obj) {
        // 更新用户表
        hfzCardTypeService.update(obj);

        return ResultDataUtil.isOk(null);
    }

    /**
     * 全部福卡
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('prize:add','prize:update')")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ResultDataUtil all(HfzCardType obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzCardType> list = hfzCardTypeService.selectAll(obj);
        return ResultDataUtil.isOk(list);
    }

}
