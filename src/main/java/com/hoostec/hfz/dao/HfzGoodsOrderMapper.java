package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzGoodsOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzGoodsOrderMapper {


    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */

    List<HfzGoodsOrder> selectAll(HfzGoodsOrder obj);

    /**
     * 关联查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzGoodsOrder> selectAllUserGoods(HfzGoodsOrder obj);

    List<HfzGoodsOrder> selectAllUserGood(HfzGoodsOrder obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzGoodsOrder obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzGoodsOrder obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);

}