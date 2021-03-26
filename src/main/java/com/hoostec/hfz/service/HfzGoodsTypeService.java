package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzGoodsTypeMapper;
import com.hoostec.hfz.entity.HfzGoodsType;
import java.util.List;

import com.hoostec.hfz.entity.HfzUser;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzGoodsTypeService {
    @Autowired
    private HfzGoodsTypeMapper hfzGoodsType;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzGoodsType obj) {
        int ret = hfzGoodsType.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzGoodsType obj) {
        int ret = hfzGoodsType.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzGoodsType> selectAll(HfzGoodsType obj) {
        List<HfzGoodsType> ret = hfzGoodsType.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzGoodsType.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzGoodsType> selectAll(HfzGoodsType obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzGoodsType> list = hfzGoodsType.selectAllThisName(obj);
        PageInfo<HfzGoodsType> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}