package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzSignMapper;
import com.hoostec.hfz.entity.HfzSign;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzSignService {
    @Autowired
    private HfzSignMapper hfzSign;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzSign obj) {
        int ret = hfzSign.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzSign obj) {
        int ret = hfzSign.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzSign> selectAll(HfzSign obj) {
        List<HfzSign> ret = hfzSign.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzSign.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzSign> selectAll(HfzSign obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzSign> list = hfzSign.selectAllUser(obj);
        PageInfo<HfzSign> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}