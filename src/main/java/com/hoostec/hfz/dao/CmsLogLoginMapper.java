package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.CmsLogLogin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsLogLoginMapper {

	/**
	 * 批量查询 （分页）
	 * 
	 * @return
	 */
	List<CmsLogLogin> selectAll();

	/**
	 * 插入
	 * 
	 * @param obj
	 * @return
	 */
	int insert(CmsLogLogin obj);

}
