package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.*;
import com.hoostec.hfz.service.*;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 每日支出
 */
@RestController
@RequestMapping("hfz/sys")
public class HfzSysController {


    @Autowired
    private HfzConfigAliService hfzConfigAliService;
    @Autowired
    private HfzConfigWxService hfzConfigWxService;
    @Autowired
    private HfzWarnService hfzWarnService;
    @Autowired
    private HfzUserService hfzUserService;
    @Autowired
    private HfzMessageService hfzMessageService;
    @Autowired
    private HfzProfitCardService hfzProfitCardService;
    @Autowired
    private HfzShareService hfzShareService;

    /**
     * 查询阿里云
     *
     * @return
     */
    @RequestMapping(value = "/hfzConfigAli", method = RequestMethod.GET)
    public ResultDataUtil hfzConfigAli(HfzConfigAli obj) {


        obj = hfzConfigAliService.selectOne();

        return ResultDataUtil.isOk(obj);
    }


    /**
     * 修改阿里云配置信息
     *
     * @return
     */
    @MyLog("修改阿里云配置信息")
    @RequestMapping(value = "/hfzConfigAliUpdate", method = RequestMethod.POST)
    public ResultDataUtil hfzConfigAliUpdate(HfzConfigAli obj) {

        return ResultDataUtil.isOk(hfzConfigAliService.update(obj));
    }


    /**
     * 查询微信配置信息
     *
     * @return
     */
    @RequestMapping(value = "/hfzConfigWx", method = RequestMethod.GET)
    public ResultDataUtil hfzConfigWx(HfzConfigWx obj) {


        obj = hfzConfigWxService.selectOne();

        return ResultDataUtil.isOk(obj);
    }


    /**
     * 修改微信基本信息
     *
     * @return
     */
    @MyLog("修改微信基本信息")
    @RequestMapping(value = "/hfzConfigWxUpdate", method = RequestMethod.POST)
    public ResultDataUtil hfzConfigWxUpdate(HfzConfigWx obj) {

        return ResultDataUtil.isOk(hfzConfigWxService.update(obj));
    }

    /**
     * 查询警告信息
     *
     * @return
     */
    @RequestMapping(value = "/warnList", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzWarn obj, PageUtil page) {
        PageInfo<HfzWarn> list = hfzWarnService.selectAll(obj, page);

        //切割多用户id   获取其姓名然后再赋值
        for (HfzWarn warn : list.getList()) {
            String ids = warn.getUserId();
            String[] idsL = ids.split(",");
            Arrays.sort(idsL);
            String userName = "";
            for (int x = 0; x < idsL.length; x++) {
                HfzUser user = new HfzUser();
                user.setId(Integer.valueOf(idsL[x]));
                user = hfzUserService.selectAll(user).get(0);

                userName += user.getNickName() + ",";
            }
            userName = userName.substring(0, userName.length() - 1);
            warn.setUserName(userName);
        }


        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 拉黑用户
     *
     * @return
     */
    @MyLog("拉黑用户")
    @Transactional
    @RequestMapping(value = "/warnUserUpdate", method = RequestMethod.POST)
    public ResultDataUtil warnUserUpdate(HfzWarn objs) {

        //批量获取  拉黑用户id
        String[] idsL = objs.getUserId().split(",");
        Arrays.sort(idsL);

        for (int x = 0; x < idsL.length; x++) {
            HfzUser obj = new HfzUser();
            obj.setId(new Integer(idsL[x]));
            //拉黑状态
            obj.setUserStatus(2);
            hfzUserService.update(obj);
        }
        //拉黑处理
        objs.setHandleStatus(2);
        hfzWarnService.update(objs);

        return ResultDataUtil.isOk(null);
    }

    /**
     * 警告用户发私信·
     *
     * @return
     */
    @MyLog("警告用户发私信")
    @Transactional
    @RequestMapping(value = "/messageInsert", method = RequestMethod.POST)
    public ResultDataUtil messageInsert(HfzWarn objs) {
        //批量获取  拉黑用户id
        String[] idsL = objs.getUserId().split(",");
        Arrays.sort(idsL);

        for (int x = 0; x < idsL.length; x++) {
            HfzMessage obj = new HfzMessage();
            obj.setUserId(new Integer(idsL[x]));
            obj.setTitle("用户异常警告");
            obj.setContent(objs.getMsg());
            obj.setReadStatus(1);
            hfzMessageService.insert(obj);
        }


        //发私信处理
        objs.setHandleStatus(3);
        hfzWarnService.update(objs);


        return ResultDataUtil.isOk(null);
    }

    /**
     * 警告用户发私信·
     *
     * @return
     */
    @MyLog("警告用户发私信")
    @Transactional
    @RequestMapping(value = "/userName", method = RequestMethod.POST)
    public ResultDataUtil userName(String userIds) {

        //批量获取  拉黑用户id
        String[] idsL = userIds.split(",");
        Arrays.sort(idsL);
        String userName = "";

        HfzUser user = new HfzUser();
        for (int x = 0; x < idsL.length; x++) {
            user.setId(new Integer(idsL[x]));
            user = hfzUserService.selectAll(user).get(0);
            userName += user.getNickName() + ",";
        }


        return ResultDataUtil.isOk(userName);
    }

    /**
     * 收益卡发放问题
     *
     * @return
     */
    @Transactional
    @RequestMapping(value = "/profitCardOnes", method = RequestMethod.GET)
    public ResultDataUtil profitCardOnes(HfzProfitCard obj) {

        obj = hfzProfitCardService.selectOne();

        return ResultDataUtil.isOk(obj);
    }

    /**
     * 收益卡更新
     *
     * @return
     */
    @MyLog("收益卡更新")
    @Transactional
    @RequestMapping(value = "/profitCardUpdates", method = RequestMethod.POST)
    public ResultDataUtil profitCardUpdates(HfzProfitCard obj) {

        return ResultDataUtil.isOk(hfzProfitCardService.update(obj));
    }








    /**
     * 查询分享配置信息
     *
     * @return
     */
    @RequestMapping(value = "/hfzConfigShare", method = RequestMethod.GET)
    public ResultDataUtil hfzConfigShare(HfzShare obj) {


        obj = hfzShareService.selectOne();

        return ResultDataUtil.isOk(obj);
    }


    /**
     * 修改分享基本信息
     *
     * @return
     */
    @MyLog("修改分享基本信息")
    @RequestMapping(value = "/hfzConfigShareUpdate", method = RequestMethod.POST)
    public ResultDataUtil hfzConfigShareUpdate(HfzShare obj) {

        return ResultDataUtil.isOk(hfzShareService.update(obj));
    }








}

