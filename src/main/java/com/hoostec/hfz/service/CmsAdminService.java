package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.CmsAdminMapper;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsAdminService {

	@Autowired
	private CmsAdminMapper cmsAdminMapper;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public CmsAdmin selectUserByName(String username) {
		return cmsAdminMapper.selectUserByName(username);
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public CmsAdmin selectOne(Integer userId) {
		return cmsAdminMapper.selectOne(userId);
	}

	/**
	 * 批量查询
	 * 
	 * @param obj
	 * @return
	 */
	public PageInfo<CmsAdmin> selectAll(CmsAdmin obj, PageUtil page) {
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<CmsAdmin> list = cmsAdminMapper.selectAll(obj);
		PageInfo<CmsAdmin> appsPageInfo = new PageInfo<>(list);
		return appsPageInfo;
	}

	/**
	 * 更新用户
	 * 
	 * @param obj
	 * @return
	 */
	public int update(CmsAdmin obj) {
		if (obj.getPassword() != null && !"".equals(obj.getPassword())) {
			obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		} else {
			obj.setPassword(null);
		}
		return cmsAdminMapper.update(obj);
	}

	/**
	 * 新增用户
	 * 
	 * @param obj
	 * @return
	 */
	public int insert(CmsAdmin obj) {
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return cmsAdminMapper.insert(obj);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param obj
	 * @return
	 */
	public int deleteAll(String[] ids) {
		return cmsAdminMapper.deleteAll(ids);
	}

}