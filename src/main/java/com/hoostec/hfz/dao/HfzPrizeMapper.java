package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzPrize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzPrizeMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzPrize> selectAll(HfzPrize obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzPrize obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzPrize obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}