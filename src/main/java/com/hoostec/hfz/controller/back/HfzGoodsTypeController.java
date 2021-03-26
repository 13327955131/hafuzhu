package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.entity.HfzUser;
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
@RequestMapping("hfz/goodsType")
public class HfzGoodsTypeController {


    @Autowired
    private HfzUserService hfzUserService;

    @Autowired
    private HfzGoodsTypeService hfzGoodsTypeService;


    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('goodsType:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzGoodsType obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzGoodsType> list = hfzGoodsTypeService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }


    /**
     *  查询全部类型
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('goodsType:list','goods:list')")
    @RequestMapping(value = "/goodsTypeList", method = RequestMethod.GET)
    public ResultDataUtil userList(HfzGoodsType obj) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzGoodsType> list=hfzGoodsTypeService.selectAll(obj);
        return ResultDataUtil.isOk(list);
    }

    /**
     *  新增类型
     *
     * @return
     */
    @MyLog("新增类型")
    @PreAuthorize("hasAnyAuthority('goodsType:list')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil goodsTypeAdd(HfzGoodsType obj) {


        return ResultDataUtil.isOk(hfzGoodsTypeService.insert(obj));
    }
    /**
     *  删除类型
     *
     * @return
     */
    @MyLog("类型删除")
    @PreAuthorize("hasAuthority('goodsType:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzGoodsTypeService.deleteAll(ids));
    }

    @MyLog("修改类型")
    @PreAuthorize("hasAuthority('goodsType:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzGoodsType obj) {
        // 更新用户表
        hfzGoodsTypeService.update(obj);

        return ResultDataUtil.isOk(null);
    }



}
