package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzUserAddressMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserAddress> selectAll(HfzUserAddress obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserAddress obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserAddress obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);


}