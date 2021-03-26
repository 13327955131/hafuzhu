package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzFeedbackMapper;
import com.hoostec.hfz.entity.HfzFeedback;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzFeedbackService {
    @Autowired
    private HfzFeedbackMapper hfzFeedback;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzFeedback obj) {
        int ret = hfzFeedback.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzFeedback obj) {
        int ret = hfzFeedback.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzFeedback> selectAll(HfzFeedback obj) {
        List<HfzFeedback> ret = hfzFeedback.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzFeedback.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzFeedback> selectAll(HfzFeedback obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzFeedback> list = hfzFeedback.selectAll(obj);
        PageInfo<HfzFeedback> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}