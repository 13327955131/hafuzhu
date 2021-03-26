package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzSwiper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzSwiperMapper {


    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzSwiper> selectAll(HfzSwiper obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzSwiper obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzSwiper obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}