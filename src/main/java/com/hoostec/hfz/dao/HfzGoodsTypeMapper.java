package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzGoodsType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzGoodsTypeMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzGoodsType> selectAll(HfzGoodsType obj);


    /**
     * 自己关联自己 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzGoodsType> selectAllThisName(HfzGoodsType obj);
    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzGoodsType obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzGoodsType obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}