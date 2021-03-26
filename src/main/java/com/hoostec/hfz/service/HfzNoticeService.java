package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzNoticeMapper;
import com.hoostec.hfz.entity.HfzNotice;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzNoticeService {
    @Autowired
    private HfzNoticeMapper hfzNotice;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzNotice obj) {
        int ret = hfzNotice.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzNotice obj) {
        int ret = hfzNotice.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzNotice> selectAll(HfzNotice obj) {
        List<HfzNotice> ret = hfzNotice.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzNotice.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzNotice> selectAll(HfzNotice obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzNotice> list = hfzNotice.selectAll(obj);
        PageInfo<HfzNotice> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}