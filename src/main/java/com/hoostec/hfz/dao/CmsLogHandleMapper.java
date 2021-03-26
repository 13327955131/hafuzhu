package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.CmsLogHandle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsLogHandleMapper {
	/**
	 * 批量查询 （分页）
	 * 
	 * @return
	 */
	List<CmsLogHandle> selectAll();

	/**
	 * 插入
	 * 
	 * @param obj
	 * @return
	 */
	int insert(CmsLogHandle obj);
}
