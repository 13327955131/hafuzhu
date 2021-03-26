package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzGoodsMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzGoods> selectAll(HfzGoods obj);

    /**
     * 关联类型表
     * @param obj
     * @return
     */
    List<HfzGoods> selectAllType(HfzGoods obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzGoods obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzGoods obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);

}