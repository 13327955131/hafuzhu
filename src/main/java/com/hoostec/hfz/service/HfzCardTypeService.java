package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzCardTypeMapper;
import com.hoostec.hfz.entity.HfzCardType;
import java.util.List;

import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzCardTypeService {
    @Autowired
    private HfzCardTypeMapper hfzCardType;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzCardType obj) {
        int ret = hfzCardType.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzCardType obj) {
        int ret = hfzCardType.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzCardType> selectAll(HfzCardType obj) {
        List<HfzCardType> ret = hfzCardType.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzCardType.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzCardType> selectAll(HfzCardType obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzCardType> list = hfzCardType.selectAll(obj);
        PageInfo<HfzCardType> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}