package com.hoostec.hfz.service;

import com.hoostec.hfz.dao.CmsMenuMapper;
import com.hoostec.hfz.entity.CmsMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsMenuService {

	@Autowired
	private CmsMenuMapper cmsMenuMapper;

	/**
	 * 批量查询
	 * 
	 * @param obj
	 * @return
	 */
	public List<CmsMenu> selectAll(CmsMenu obj) {
		List<CmsMenu> list = cmsMenuMapper.selectAll(obj);
		return list;
	}

	/**
	 * 更新菜单
	 * 
	 * @param obj
	 * @return
	 */
	public int update(CmsMenu obj) {

		return cmsMenuMapper.update(obj);
	}

	/**
	 * 新增菜单
	 * 
	 * @param obj
	 * @return
	 */
	public int insert(CmsMenu obj) {
		return cmsMenuMapper.insert(obj);
	}

	/**
	 * 批量删除菜单
	 * 
	 * @param obj
	 * @return
	 */
	public int deleteAll(String[] ids) {
		return cmsMenuMapper.deleteAll(ids);
	}

	/**
	 * 根据角色Id 查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<CmsMenu> selectRoleMenus(Integer roleId) {

		return cmsMenuMapper.selectRoleMenus(roleId);
	}

}