package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzWarn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzWarnMapper {
    int insert(HfzWarn record);

    List<HfzWarn> selectAll(HfzWarn obj);

    int update(HfzWarn record);
}