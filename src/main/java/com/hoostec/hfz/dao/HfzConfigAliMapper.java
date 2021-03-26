package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzConfigAli;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfzConfigAliMapper {
    int update(HfzConfigAli record);

    HfzConfigAli selectOne();
}