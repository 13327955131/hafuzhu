package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzPayDay;
import com.hoostec.hfz.entity.HfzSwiper;
import com.hoostec.hfz.service.HfzPayDayService;
import com.hoostec.hfz.service.HfzSwiperService;
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
 * 轮播图
 */
@RestController
@RequestMapping("hfz/swiper")
public class HfzSwiperController {
    @Autowired
    private HfzSwiperService hfzSwiperService;
    /**
     * 查询全部
     *
     * @return
     */
    @PreAuthorize("hasAuthority('swiper:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzSwiper obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);

        PageInfo<HfzSwiper> list = hfzSwiperService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     * 新增轮播图
     *
     * @return
     */
    @MyLog("新增轮播图")
    @PreAuthorize("hasAuthority('swiper:add')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil swiperAdd(HfzSwiper obj) {

        return ResultDataUtil.isOk(hfzSwiperService.insert(obj));
    }
    /**
     * 编辑轮播图
     *
     * @return
     */
    @MyLog("编辑轮播图")
    @PreAuthorize("hasAuthority('swiper:add')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil update(HfzSwiper obj) {

        return ResultDataUtil.isOk(hfzSwiperService.update(obj));
    }


    /**
     *  删除轮播图
     *
     * @return
     */
    @MyLog("删除任务")
    @PreAuthorize("hasAuthority('swiper:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzSwiperService.deleteAll(ids));
    }





}
