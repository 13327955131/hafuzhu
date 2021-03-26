package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzConfigWx;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfzConfigWxMapper {
    int update(HfzConfigWx record);

    HfzConfigWx selectOne();
}