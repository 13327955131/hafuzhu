package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzFeedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzFeedbackMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzFeedback> selectAll(HfzFeedback obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzFeedback obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzFeedback obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);


}