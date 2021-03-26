package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzSign;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzSignMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzSign> selectAll(HfzSign obj);  /**
     * 批量查询 关联用户表 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzSign> selectAllUser(HfzSign obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzSign obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzSign obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}