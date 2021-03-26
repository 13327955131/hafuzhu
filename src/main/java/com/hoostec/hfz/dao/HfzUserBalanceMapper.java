package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUserBalance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzUserBalanceMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserBalance> selectAll(HfzUserBalance obj);

    /**
     * 关联用户表查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserBalance> selectAllUser(HfzUserBalance obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserBalance obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserBalance obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}