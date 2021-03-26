package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzProfitPickRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzProfitPickRecordMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzProfitPickRecord> selectAll(HfzProfitPickRecord obj);
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzProfitPickRecord> selectAllUser(HfzProfitPickRecord obj);
    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzProfitPickRecord obj);


    /**
     * 获取每日审核通过的支出
     * @param obj
     * @return
     */
    int selectDayPay(HfzProfitPickRecord obj);
   /**
     * 数量
     *
     * @param obj
     * @return
     */
    int selectCount(HfzProfitPickRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzProfitPickRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}