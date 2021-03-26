package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzVersionMapper;
import com.hoostec.hfz.entity.HfzVersion;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzVersionService {
    @Autowired
    private HfzVersionMapper hfzVersion;

    /**
     * 插入接口
     *
     * @return
     **/
    public int insert(HfzVersion obj) {
        int ret = hfzVersion.insert(obj);
        return ret;
    }

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzVersion obj) {
        int ret = hfzVersion.update(obj);
        return ret;
    }

    /**
     * 查询全部
     *
     * @return
     **/
    public List<HfzVersion> selectAll(HfzVersion obj) {
        List<HfzVersion> ret = hfzVersion.selectAll(obj);
        return ret;
    }



    /**
     * 删除全部del_status=-1
     *
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzVersion.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzVersion> selectAll(HfzVersion obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzVersion> list = hfzVersion.selectAll(obj);
        PageInfo<HfzVersion> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}