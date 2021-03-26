package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzVersion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzVersionMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzVersion> selectAll(HfzVersion obj);



    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzVersion obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzVersion obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}