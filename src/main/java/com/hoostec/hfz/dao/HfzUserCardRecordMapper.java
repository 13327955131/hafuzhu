package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUserCardRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzUserCardRecordMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserCardRecord> selectAll(HfzUserCardRecord obj); /**
     * 批量查询关联用户表 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserCardRecord> selectAllUser(HfzUserCardRecord obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserCardRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserCardRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}