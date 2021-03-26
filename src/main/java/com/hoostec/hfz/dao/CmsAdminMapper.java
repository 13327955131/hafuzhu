package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.CmsAdmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsAdminMapper {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	CmsAdmin selectUserByName(String username);

	/**
	 * 查询
	 * 
	 * @param userId
	 * @return
	 */
	CmsAdmin selectOne(Integer userId);

	/**
	 * 批量查询 （分页）
	 * 
	 * @param obj
	 * @return
	 */
	List<CmsAdmin> selectAll(CmsAdmin obj);

	/**
	 * 更新
	 * 
	 * @param obj
	 * @return
	 */
	int update(CmsAdmin obj);

	/**
	 * 插入
	 * 
	 * @param obj
	 * @return
	 */
	int insert(CmsAdmin obj);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	int deleteAll(String[] ids);

}