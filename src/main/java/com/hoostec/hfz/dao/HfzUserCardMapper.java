package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUserCard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzUserCardMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserCard> selectAll(HfzUserCard obj); /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserCard> selectAllUser(HfzUserCard obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserCard obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserCard obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}