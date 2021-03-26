package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzCardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzCardTypeMapper {


    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzCardType> selectAll(HfzCardType obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzCardType obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzCardType obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);


}