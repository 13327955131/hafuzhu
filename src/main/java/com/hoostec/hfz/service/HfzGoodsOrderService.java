package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.HfzGoodsOrderMapper;
import com.hoostec.hfz.entity.HfzGoodsOrder;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class HfzGoodsOrderService {
    @Autowired
    private HfzGoodsOrderMapper hfzGoodsOrder;

    /**
     * 插入接口
     * 
     * @return
     **/
    public int insert(HfzGoodsOrder obj) {
        int ret = hfzGoodsOrder.insert(obj);
        return ret;
    }

    /**
     * 修改
     * 
     * @return
     **/
    public int update(HfzGoodsOrder obj) {
        int ret = hfzGoodsOrder.update(obj);
        return ret;
    }

    /**
     * 查询全部
     * 
     * @return
     **/
    public List<HfzGoodsOrder> selectAll(HfzGoodsOrder obj) {
        List<HfzGoodsOrder> ret = hfzGoodsOrder.selectAll(obj);
        return ret;
    }

    /**
     * 删除全部del_status=-1
     * 
     * @return
     **/
    public int deleteAll(String[] ids) {
        int ret = hfzGoodsOrder.deleteAll(ids);
        return ret;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public PageInfo<HfzGoodsOrder> selectAll(HfzGoodsOrder obj, PageUtil page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        List<HfzGoodsOrder> list = hfzGoodsOrder.selectAllUserGoods(obj);
        PageInfo<HfzGoodsOrder> appsPageInfo = new PageInfo<>(list);
        return appsPageInfo;
    }

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    public List<HfzGoodsOrder> selectAllUserGood(HfzGoodsOrder obj) {
        List<HfzGoodsOrder> list = hfzGoodsOrder.selectAllUserGood(obj);
        return list;
    }
}