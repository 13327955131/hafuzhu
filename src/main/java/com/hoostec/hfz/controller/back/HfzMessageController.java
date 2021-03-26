package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.entity.HfzMessage;
import com.hoostec.hfz.entity.HfzUserAddress;
import com.hoostec.hfz.service.HfzGoodsTypeService;
import com.hoostec.hfz.service.HfzMessageService;
import com.hoostec.hfz.service.HfzUserAddressService;
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
@RequestMapping("hfz/message")
public class HfzMessageController {


    @Autowired
    private HfzMessageService hfzMessageService;



    /**
     *  私信列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('message:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzMessage obj, PageUtil page) {
        PageInfo<HfzMessage> list = hfzMessageService.selectAll(obj, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     *  私信列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('message:add')")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultDataUtil insert(HfzMessage obj) {


        hfzMessageService.insert(obj);

        return ResultDataUtil.isOk(null);
    }


}
