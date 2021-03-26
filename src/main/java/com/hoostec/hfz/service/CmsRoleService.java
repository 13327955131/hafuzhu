package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.CmsRoleMapper;
import com.hoostec.hfz.entity.CmsRole;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsRoleService {

	@Autowired
	private CmsRoleMapper cmsRoleMapper;

	/**
	 * 批量查询
	 * 
	 * @param obj
	 * @return
	 */
	public PageInfo<CmsRole> selectAll(CmsRole obj, PageUtil page) {
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<CmsRole> list = cmsRoleMapper.selectAll(obj);
		PageInfo<CmsRole> appsPageInfo = new PageInfo<>(list);
		return appsPageInfo;
	}

	/**
	 * 更新角色
	 * 
	 * @param obj
	 * @return
	 */
	public int update(CmsRole obj) {

		return cmsRoleMapper.update(obj);
	}

	/**
	 * 新增角色
	 * 
	 * @param obj
	 * @return
	 */
	public int insert(CmsRole obj) {
		return cmsRoleMapper.insert(obj);
	}

	/**
	 * 批量删除角色
	 * 
	 * @param obj
	 * @return
	 */
	public int deleteAll(String[] ids) {
		return cmsRoleMapper.deleteAll(ids);
	}

}