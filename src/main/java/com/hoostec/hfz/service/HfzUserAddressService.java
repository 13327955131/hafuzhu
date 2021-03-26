package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzUserAddressMapper;
import com.hoostec.hfz.entity.HfzUserAddress;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzUserAddressService {
    @Autowired
    private HfzUserAddressMapper hfzUserAddress;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzUserAddress obj) {
        int ret = hfzUserAddress.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzUserAddress obj) {
        int ret = hfzUserAddress.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzUserAddress> selectAll(HfzUserAddress obj) {
        List<HfzUserAddress> ret = hfzUserAddress.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzUserAddress.deleteAll(ids);
        return ret;
    }
    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzUserAddress> selectAll(HfzUserAddress obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzUserAddress> list = hfzUserAddress.selectAll(obj);
        PageInfo<HfzUserAddress> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}