package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzFeedback;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.service.HfzFeedbackService;
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
@RequestMapping("hfz/feedback")
public class HfzFeedbackController {


    @Autowired
    private HfzFeedbackService hfzFeedbackService;



    /**
     * 类型列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('feedback:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzFeedback obj, PageUtil page) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzFeedback> list = hfzFeedbackService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }





}
