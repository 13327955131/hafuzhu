package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzMessageMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzMessage> selectAll(HfzMessage obj);
    /**
     * 批量查询 关联用户表 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzMessage> selectAllUser(HfzMessage obj);

    /**
     * 批量查询
     *
     * @param obj
     * @return
     */
    List<HfzMessage> selectMessageList(HfzMessage obj);



    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzMessage obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzMessage obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);


}