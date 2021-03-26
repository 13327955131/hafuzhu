package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzProfitPickRecord;
import com.hoostec.hfz.entity.HfzUserIntegralRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzUserIntegralRecordMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserIntegralRecord> selectAll(HfzUserIntegralRecord obj);

    /**
     *  根据日期去重并排序
     * @param obj
     * @return
     */
    List<HfzUserIntegralRecord> selectAllGroupSort(HfzUserIntegralRecord obj);




    /**
     * 关联用户表批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserIntegralRecord> selectAllUser(HfzUserIntegralRecord obj);
    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserIntegralRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserIntegralRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}