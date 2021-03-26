package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzUserBalanceMapper;
import com.hoostec.hfz.entity.HfzUserBalance;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzUserBalanceService {
    @Autowired
    private HfzUserBalanceMapper hfzUserBalance;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzUserBalance obj) {
        int ret = hfzUserBalance.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzUserBalance obj) {
        int ret = hfzUserBalance.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzUserBalance> selectAll(HfzUserBalance obj) {
        List<HfzUserBalance> ret = hfzUserBalance.selectAll(obj);
        return ret;
    }

    /**
     * 关联用户表查询
     *
     * @return
     **/
    public List<HfzUserBalance> selectAllUser(HfzUserBalance obj) {
        List<HfzUserBalance> ret = hfzUserBalance.selectAllUser(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzUserBalance.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzUserBalance> selectAll(HfzUserBalance obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzUserBalance> list = hfzUserBalance.selectAllUser(obj);
        PageInfo<HfzUserBalance> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }


}