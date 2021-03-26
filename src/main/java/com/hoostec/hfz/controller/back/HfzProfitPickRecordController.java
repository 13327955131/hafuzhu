package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.HfzConfigWx;
import com.hoostec.hfz.entity.HfzMessage;
import com.hoostec.hfz.entity.HfzProfitPickRecord;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.service.HfzConfigWxService;
import com.hoostec.hfz.service.HfzMessageService;
import com.hoostec.hfz.service.HfzProfitPickRecordService;
import com.hoostec.hfz.service.HfzUserService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import com.hoostec.hfz.utils.wx.pay.OtherUtils;
import com.hoostec.hfz.utils.wx.pay.WeChatWithdrawUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 财务统计------收益提现记录
 */
@RestController
@RequestMapping("hfz/profitPickRecord")
public class HfzProfitPickRecordController {
    @Autowired
    private HfzProfitPickRecordService hfzProfitPickRecordService;
    @Autowired
    private HfzUserService hfzUserService;
    @Autowired
    private HfzMessageService hfzMessageService;
    @Autowired
    private HfzConfigWxService hfzConfigWxService;


    /**
     * 收益提现列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('profitPickRecord:list','use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzProfitPickRecord hfzProfitRecord, PageUtil page) {
        hfzProfitRecord.setDelStatus(Constant.DEL_STATUS_ONE);
        PageInfo<HfzProfitPickRecord> list = hfzProfitPickRecordService.selectAll(hfzProfitRecord, page);

        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }

    /**
     * 审核
     *
     * @return
     */
    @MyLog("审核")
    @PreAuthorize("hasAuthority('profitPickRecord:list')")
    @RequestMapping(value = "/updateSH", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil shen(HfzProfitPickRecord obj) {
        int stuts = obj.getVerifyStatus();
        int userId = obj.getUserId();
        obj.setUserId(null);
        //审核之后   发送私信通知用户
        HfzMessage mess = new HfzMessage();
        mess.setUserId(userId);
        if (stuts == 2) {
            //审核成功
            mess.setTitle("审核成功");
        } else if (stuts == 3) {
            //审核失败
            mess.setTitle("审核失败");
        }
        mess.setContent(obj.getMsg());
        hfzMessageService.insert(mess);
        //审核之后   发送私信通知用户 end

        //支付


        //获取支付用户 openId
        HfzUser user = new HfzUser();
        user.setId(userId);
        user = hfzUserService.selectAll(user).get(0);

        HfzConfigWx wx = new HfzConfigWx();
        wx = hfzConfigWxService.selectOne();


        Map<String, String> params = new HashMap<>();
        params.put("mch_appid", wx.getAppId());            //商戶號信息
        params.put("appSecret", wx.getAppSecret());
        params.put("mchid", wx.getMchId());
        params.put("mchsecret", wx.getMchSecret());
        params.put("spbill_create_ip", wx.getSpbillCreateIp());


        // TODO 任务金额
        params.put("amount", "30");     //任務金額

        params.put("partner_trade_no", System.currentTimeMillis() + "");    // 订单号
        params.put("openid", user.getWxId());
        String result = null;
        try {
            result = WeChatWithdrawUtils.withdrawRequestOnce(params, 3000, 3000, true);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> resultMap = OtherUtils.readStringXmlOut(result);

        if (resultMap.containsKey("result_code") && "SUCCESS".equals(resultMap.getOrDefault("result_code", ""))) {
            System.out.println("支付成功");
            hfzProfitPickRecordService.update(obj);
        } else {
            System.out.println("支付失敗");
        }


        return ResultDataUtil.isOk(1);
    }


}
