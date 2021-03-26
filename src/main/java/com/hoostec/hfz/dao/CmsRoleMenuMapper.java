package com.hoostec.hfz.dao;

import com.hoostec.hfz.entity.CmsRoleMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CmsRoleMenuMapper {
	/**
	 * 批量插入角色权限
	 * 
	 * @param obj
	 * @return
	 */
	int insertAllByRole(CmsRoleMenu obj);

	/**
	 * 批量删除角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteAllByRole(Integer roleId);

	/**
	 * 查询角色权限
	 * 
	 * @param obj
	 * @return
	 */
	Integer[] selectAuthIds(Integer roleId);

}