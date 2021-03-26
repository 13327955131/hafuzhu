package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzTaskMapper;
import com.hoostec.hfz.entity.HfzTask;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzTaskService {
    @Autowired
    private HfzTaskMapper hfzTask;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzTask obj) {
        int ret = hfzTask.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzTask obj) {
        int ret = hfzTask.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzTask> selectAll(HfzTask obj) {
        List<HfzTask> ret = hfzTask.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzTask.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzTask> selectAll(HfzTask obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzTask> list = hfzTask.selectAll(obj);
        PageInfo<HfzTask> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}