package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.*;
import com.hoostec.hfz.service.*;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.DateUtils;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.hoostec.hfz.utils.AddressUtil.getAddressByIp;


/**
 *   平台用户管理
 */
@RestController
@RequestMapping("hfz/console")
public class HfzIndexController {




    @Autowired
    private HfzGoodsService hfzGoodsService;
    @Autowired
    private HfzSignService hfzSignService;
    @Autowired
    private HfzUserService hfzUserService;
    @Autowired
    private HfzUserTaskRecordService hfzUserTaskRecordService;
    @Autowired
    private HfzPayDayService hfzPayDayService;
    @Autowired
    private HfzUserIntegralRecordService hfzUserIntegralRecordService;
    @Autowired
    private HfzProfitRecordService hfzProfitRecordService;
    @Autowired
    private HfzProfitPickRecordService hfzProfitPickRecordService;



    /**
     * 根据用户ip判断属于哪个省份
     *
     * @return
     */
    @MyLog("根据用户ip判断属于哪个省份")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/lastLoginIp", method = RequestMethod.POST)
    public ResultDataUtil lastLoginIp(HfzUser obj) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzUser> list = hfzUserService.selectProvincia(obj);
        return ResultDataUtil.isOk(list);
    }


    /**
     * 查询今日新增
     *
     * @return
     */
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/userInsert", method = RequestMethod.POST)
    public ResultDataUtil userInsert(HfzUser obj) {
        JSONObject json=new JSONObject();
        //  新增人数
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        //全部人数
        json.put("userAll",hfzUserService.selectCountIndex(obj));

        //获取当日时间
        String times=DateUtils.getCurrentTimes();
        obj.setRegisterTime(times);
        //当日新增人数
        json.put("userThs",hfzUserService.selectCountIndex(obj));

        //任务
        HfzUserTaskRecord task=new HfzUserTaskRecord();
        task.setDelStatus(Constant.DEL_STATUS_ONE);


       // 1 视频   2 浏览
        task.setType(1);
        //1看视频 2    点击下载
        //全部视频查看数量      task.setDegree(null);
        json.put("taskVideoAll",hfzUserTaskRecordService.selectCountIndex(task));
        task.setDegree(2);
        //全部视频下载量
        json.put("taskVideoDownloadAll",hfzUserTaskRecordService.selectCountIndex(task));


        // 1 视频   2 浏览
        task.setType(2);
        //1看咨询  2  点击下载
        //全部浏览量
        task.setDegree(null);
        json.put("taskBrowseAll",hfzUserTaskRecordService.selectCountIndex(task));
        task.setDegree(2);
        //全部浏览下载量
        json.put("taskBrowseDownloadAll",hfzUserTaskRecordService.selectCountIndex(task));


        //获取当日时间
        times=DateUtils.getCurrentTimess();
        task.setDayTime(times);
        task.setType(1);
        //1看视频 2    点击下载
        task.setDegree(null);
        json.put("taskVideoThs",hfzUserTaskRecordService.selectCountIndex(task));
        task.setDegree(2);
        json.put("taskVideoDownloadThs",hfzUserTaskRecordService.selectCountIndex(task));


        // 1 视频   2 浏览
        task.setType(2);
        //1看咨询  2  点击下载
        //全部浏览量
        task.setDegree(null);
        json.put("taskBrowseThs",hfzUserTaskRecordService.selectCountIndex(task));
        task.setDegree(2);
        //全部浏览下载量
        json.put("taskBrowseDownloadThs",hfzUserTaskRecordService.selectCountIndex(task));

        return ResultDataUtil.isOk(json);
    }


    /**
     * 财务支出
     *
     * @return
     */
    @MyLog("财务支出查询")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/payDay", method = RequestMethod.POST)
    public ResultDataUtil payDay(HfzPayDay obj) {
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        List<HfzPayDay> list = hfzPayDayService.selectAllPay(obj);

        return ResultDataUtil.isOk(list);
    }

    /**
     * 当日积分排行
     *
     * @return
     */
    @MyLog("获取当日福币排名")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/userIntegralRecord", method = RequestMethod.POST)
    public ResultDataUtil userIntegralRecord(HfzUserIntegralRecord obj) {
        JSONObject json=new JSONObject();


        PageUtil page =new PageUtil();
        page.setPageSize(5);
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        //查询全部前五排名
        PageInfo<HfzUserIntegralRecord> list = hfzUserIntegralRecordService.selectAllGroupSort(obj,page);
        json.put("groupSortAll",list.getList());

        //查询当日前五排名
        obj.setUseTime("不为null就是查询当日");
        list = hfzUserIntegralRecordService.selectAllGroupSort(obj,page);
        json.put("groupSortThs",list.getList());


        return ResultDataUtil.isOk(json);
    }
    /**
     * 当日收益排行
     *
     * @return
     */
    @MyLog("获取当日收益排行")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/profitRecordSort", method = RequestMethod.POST)
    public ResultDataUtil profitRecordSort(HfzProfitRecord obj) {
        JSONObject json=new JSONObject();
        PageUtil page =new PageUtil();
        page.setPageSize(5);
        obj.setDelStatus(Constant.DEL_STATUS_ONE);
        //查询全部前五排名
        PageInfo<HfzProfitRecord> list = hfzProfitRecordService.selectAllGroupSort(obj,page);
        json.put("groupSortAll",list.getList());
        //查询当日前五排名
        obj.setGetTime("不为null就是查询当日");
        list = hfzProfitRecordService.selectAllGroupSort(obj,page);
        json.put("groupSortThs",list.getList());
        return ResultDataUtil.isOk(json);
    }

    /**
     * 获取收徒排行
     *
     * @return
     */
    @MyLog("获取收徒排行")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/userParentCount", method = RequestMethod.POST)
    public ResultDataUtil userParentCount(HfzUser obj) {
        List<HfzUser> list=hfzUserService.selectParent(obj);
        return ResultDataUtil.isOk(list);
    }
    /**
     * 获取收徒排行
     *
     * @return
     */
    @MyLog("获取收徒排行")
    @PreAuthorize("hasAuthority('console:list')")
    @RequestMapping(value = "/profitPickRecordCount", method = RequestMethod.POST)
    public ResultDataUtil profitPickRecordCount(HfzProfitPickRecord obj) {
        JSONObject json=new JSONObject();
        obj.setVerifyStatus(1);
        int  count=hfzProfitPickRecordService.selectCountIindex(obj);
        json.put("待审核",count);
        obj.setVerifyStatus(2);
        count=hfzProfitPickRecordService.selectCountIindex(obj);
        json.put("审核成功",count);
        obj.setVerifyStatus(3);
        count=hfzProfitPickRecordService.selectCountIindex(obj);
        json.put("审核失败",count);
        return ResultDataUtil.isOk(json);
    }






}
