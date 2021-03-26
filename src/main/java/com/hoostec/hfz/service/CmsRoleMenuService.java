package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.CmsRoleMenuMapper;
import com.hoostec.hfz.entity.CmsRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsRoleMenuService {

	@Autowired
	private CmsRoleMenuMapper cmsRoleMenuMapper;

	/**
	 * 批量插入角色权限
	 * 
	 * @param obj
	 * @return
	 */
	public int insertAllByRole(CmsRoleMenu obj) {
		return cmsRoleMenuMapper.insertAllByRole(obj);
	}

	/**
	 * 批量删除角色权限
	 * 
	 * @param obj
	 * @return
	 */
	public int deleteAllByRole(Integer roleId) {
		return cmsRoleMenuMapper.deleteAllByRole(roleId);
	}

	/**
	 * 查询角色权限
	 * 
	 * @param obj
	 * @return
	 */
	public Integer[] selectAuthIds(Integer roleId) {
		return cmsRoleMenuMapper.selectAuthIds(roleId);
	}
}