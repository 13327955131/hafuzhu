package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzGoodsMapper;
import com.hoostec.hfz.entity.HfzGoods;
import java.util.List;

import com.hoostec.hfz.entity.HfzGoodsType;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

@Service
public class HfzGoodsService {
    @Autowired
    private HfzGoodsMapper hfzGoods;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzGoods obj) {
        int ret = hfzGoods.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzGoods obj) {
        int ret = hfzGoods.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzGoods> selectAll(HfzGoods obj) {
        List<HfzGoods> ret = hfzGoods.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzGoods.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzGoods> selectAll(HfzGoods obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzGoods> list = hfzGoods.selectAllType(obj);
        PageInfo<HfzGoods> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }
}