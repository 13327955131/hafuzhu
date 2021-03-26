package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HfzUserMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUser> selectAll(HfzUser obj);


    //根据用户IP 获取省份
    List<HfzUser> selectProvincia(HfzUser obj);

    //获取用户有多少徒弟
    List<HfzUser> selectParent(HfzUser obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUser obj);

    /**
     * 查询数量
     * @param obj
     * @return
     */
    int selectCount(HfzUser obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUser obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);


    boolean selectPhoneExist(@Param("phone") String phone);

}