package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzLuckDrawRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzLuckDrawRecordMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzLuckDrawRecord> selectAll(HfzLuckDrawRecord obj);

    List<HfzLuckDrawRecord> selectAllUser(HfzLuckDrawRecord obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzLuckDrawRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzLuckDrawRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}