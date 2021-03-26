package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzWarnMapper;
import com.hoostec.hfz.entity.HfzGoods;
import com.hoostec.hfz.entity.HfzWarn;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzWarnService {
    @Autowired
    private HfzWarnMapper hfzWarn;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzWarn obj) {
        int ret = hfzWarn.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzWarn obj) {
        int ret = hfzWarn.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzWarn> selectAll(HfzWarn obj) {
        List<HfzWarn> ret = hfzWarn.selectAll(obj);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzWarn> selectAll(HfzWarn obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzWarn> list = hfzWarn.selectAll(obj);
        PageInfo<HfzWarn> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

}