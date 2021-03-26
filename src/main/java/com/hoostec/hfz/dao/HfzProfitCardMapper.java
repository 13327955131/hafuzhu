package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.HfzProfitCard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfzProfitCardMapper {

    int update(HfzProfitCard record);

    HfzProfitCard selectOne();
}