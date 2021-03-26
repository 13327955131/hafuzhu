package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzNoticeMapper {


    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzNotice> selectAll(HfzNotice obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzNotice obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzNotice obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}