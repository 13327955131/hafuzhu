package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzShare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfzShareMapper {
    int update(HfzShare record);

    HfzShare selectOne();
}