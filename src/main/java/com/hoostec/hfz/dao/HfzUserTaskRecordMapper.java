package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzUserTaskRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzUserTaskRecordMapper {
    /**
     * 批量查询 （分页）
     *
     * @param obj
     * @return
     */
    List<HfzUserTaskRecord> selectAll(HfzUserTaskRecord obj);

    List<HfzUserTaskRecord> selectAllUserTask(HfzUserTaskRecord obj);

    /**
     * 更新
     *
     * @param obj
     * @return
     */
    int update(HfzUserTaskRecord obj);



    //任务完成数求和
    int selectNumber(HfzUserTaskRecord obj);
    //数量
    int selectCount(HfzUserTaskRecord obj);

    /**
     * 插入
     *
     * @param obj
     * @return
     */
    int insert(HfzUserTaskRecord obj);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteAll(String[] ids);
}