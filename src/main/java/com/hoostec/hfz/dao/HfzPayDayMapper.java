package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzPayDay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HfzPayDayMapper {

    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzPayDay> selectAll(HfzPayDay obj);

//财务支出   首页专用
    List<HfzPayDay> selectAllPay(HfzPayDay obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzPayDay obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzPayDay obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}