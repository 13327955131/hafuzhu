package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.entity.HfzVersion;
import com.hoostec.hfz.service.HfzGoodsTypeService;
import com.hoostec.hfz.service.HfzUserService;
import com.hoostec.hfz.service.HfzVersionService;
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
 *   版本管理
 */
@RestController
@RequestMapping("hfz/version")
public class HfzVersionController {


    @Autowired
    private HfzVersionService hfzVersionService;




    /**
     * 版本列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('version:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzVersion obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzVersion> list = hfzVersionService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }




    /**
     *  新增版本
     *
     * @return
     */
    @MyLog("新增版本")
    @PreAuthorize("hasAnyAuthority('version:list')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil goodsTypeAdd(HfzVersion obj) {


        return ResultDataUtil.isOk(hfzVersionService.insert(obj));
    }
    /**
     *  删除类型
     *
     * @return
     */
    @MyLog("版本删除")
    @PreAuthorize("hasAuthority('version:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzVersionService.deleteAll(ids));
    }

    @MyLog("修改版本")
    @PreAuthorize("hasAuthority('version:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzVersion obj) {
        // 更新用户表
        hfzVersionService.update(obj);

        return ResultDataUtil.isOk(null);
    }



}
