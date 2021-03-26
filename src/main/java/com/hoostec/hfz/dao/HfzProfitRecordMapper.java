package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzProfitRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzProfitRecordMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzProfitRecord> selectAll(HfzProfitRecord obj);


    //关联收益提现表
    List<HfzProfitRecord> selectProfitRecord(HfzProfitRecord obj);

    //去重  求和  排序
    List<HfzProfitRecord> selectAllGroupSort(HfzProfitRecord obj);


    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzProfitRecord> selectAllUser(HfzProfitRecord obj);
    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzProfitRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzProfitRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}