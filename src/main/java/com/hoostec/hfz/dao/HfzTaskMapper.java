package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzTaskMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzTask> selectAll(HfzTask obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzTask obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzTask obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}