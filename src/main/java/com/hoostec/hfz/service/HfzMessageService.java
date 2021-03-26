package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzMessageMapper;
import com.hoostec.hfz.entity.HfzMessage;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzMessageService {
    @Autowired
    private HfzMessageMapper hfzMessage;

    /**
     * 插入接口
     *
     * @return
     **/
    public int insert(HfzMessage obj) {
        int ret = hfzMessage.insert(obj);
        return ret;
    }

    /**
     * 修改
     *
     * @return
     **/
    public int update(HfzMessage obj) {
        int ret = hfzMessage.update(obj);
        return ret;
    }

    /**
     * 查询全部
     *
     * @return
     **/
    public List<HfzMessage> selectAll(HfzMessage obj) {
        List<HfzMessage> ret = hfzMessage.selectAll(obj);
        return ret;
    }



    /**
     * 删除全部del_status=-1
     *
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzMessage.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzMessage> selectAll(HfzMessage obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzMessage> list = hfzMessage.selectAllUser(obj);
        PageInfo<HfzMessage> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}