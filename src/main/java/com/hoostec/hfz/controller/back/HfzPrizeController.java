package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzPrize;
import com.hoostec.hfz.service.HfzPrizeService;
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
@RequestMapping("hfz/prize")
public class HfzPrizeController {


    @Autowired
    private HfzPrizeService hfzPrizeService;


    /**
     * 奖品列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('prize:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzPrize obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzPrize> list = hfzPrizeService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     * 新增奖品
     *
     * @return
     */
    @MyLog("新增奖品")
    @PreAuthorize("hasAuthority('prize:add')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDataUtil add(HfzPrize obj) {
        hfzPrizeService.insert(obj);
        return ResultDataUtil.isOk(null);
    }

    /**
     * 修改奖品
     *
     * @return
     */
    @MyLog("修改奖品")
    @PreAuthorize("hasAuthority('prize:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultDataUtil update(HfzPrize obj) {
        hfzPrizeService.update(obj);
        return ResultDataUtil.isOk(null);
    }


    /**
     * 奖品删除
     *
     * @param request
     * @return
     */
    @MyLog("奖品删除")
    @PreAuthorize("hasAuthority('prize:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzPrizeService.deleteAll(ids));
    }



    /**
     *  查询抽奖奖品数量
     *
     * @return
     */
    @MyLog("查询抽奖奖品数量")
    @PreAuthorize("hasAuthority('prize:add')")
    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public ResultDataUtil numbers(HfzPrize obj) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        obj.setPrizeType(1);
        return ResultDataUtil.isOk(hfzPrizeService.selectAll(obj).size());
    }


    /**
     * 修改奖品
     *
     * @return
     */
    @MyLog("批量修改奖品")
    @Transactional
    @RequestMapping(value = "/updateAll", method = RequestMethod.GET)
    public ResultDataUtil updateAll(String listPrize) {
        List<HfzPrize> list = new Gson().fromJson(listPrize, new TypeToken<List<HfzPrize>>() {
        }.getType());
        for (HfzPrize prize : list) {
            hfzPrizeService.update(prize);
        }


        return ResultDataUtil.isOk(null);
    }



}
