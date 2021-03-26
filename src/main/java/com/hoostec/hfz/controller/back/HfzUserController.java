package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.service.CmsMenuService;
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
@RequestMapping("hfz/use")
public class HfzUserController {


    @Autowired
    private HfzUserService hfzUserService;


    /**
     * 角色列表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('use:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDataUtil menuList(HfzUser hfzUser, PageUtil page) {
        hfzUser.setDelStatus(Constant.DEL_STATUS_ONE);
        //搜索时候使用
        if(hfzUser.getNickName()!=null){
            if(hfzUser.getNickName().equals("")){
                hfzUser.setNickName(null);
            }
        }

        PageInfo<HfzUser> list = hfzUserService.selectAll(hfzUser, page);
        return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
    }
    /**
     * 用户新增
     *
     * @return
     */
    @MyLog("用户新增")
    @PreAuthorize("hasAuthority('use:add')")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultDataUtil useAdd(HfzUser user) {
        //判断该手机号是否存在
        List<HfzUser> listUse=hfzUserService.selectAll(new HfzUser(user.getPhone()));


        if ( listUse.size()>0) {
            return ResultDataUtil.isError(Constant.ERROR_CODE_1003, "手机号已存在！");
        } else {
            // 插入用户表
            user.setDelStatus(1);
           hfzUserService.insert(user);

            return ResultDataUtil.isOk(null);
        }
    }


    /**
     * 用户删除
     *
     * @param request
     * @return
     */
    @MyLog("用户删除")
    @PreAuthorize("hasAuthority('use:delete')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDataUtil menuDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids[]");
        return ResultDataUtil.isOk(hfzUserService.deleteAll(ids));
    }

    /**
     * 用户更新
     *
     * @return
     */
    @MyLog("用户更新")
    @PreAuthorize("hasAuthority('use:update')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public ResultDataUtil menuUpdate(HfzUser user) {
        // 更新用户表
        hfzUserService.update(user);

        return ResultDataUtil.isOk(null);
    }


    /**
     * 获取全部用户列表
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('userTaskRecord:list','use:list','profitPickRecord:list','record:list','balance:list','profitPickRecord:list','userIntegralRecord:list','balance:list','userCardRecord:list','userCard:list','sign:list')")
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public ResultDataUtil userList() {
        List<HfzUser> list=hfzUserService.selectAll(new HfzUser());

        return ResultDataUtil.isOk(list);
    }












}
