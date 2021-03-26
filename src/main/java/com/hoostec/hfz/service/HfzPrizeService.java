package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzPrizeMapper;
import com.hoostec.hfz.entity.HfzPrize;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzPrizeService {
    @Autowired
    private HfzPrizeMapper hfzPrize;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzPrize obj) {
        int ret = hfzPrize.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzPrize obj) {
        int ret = hfzPrize.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzPrize> selectAll(HfzPrize obj) {
        List<HfzPrize> ret = hfzPrize.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzPrize.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzPrize> selectAll(HfzPrize obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzPrize> list = hfzPrize.selectAll(obj);
        PageInfo<HfzPrize> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}