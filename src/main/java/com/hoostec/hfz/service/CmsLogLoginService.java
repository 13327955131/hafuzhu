package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.CmsLogLoginMapper;
import com.hoostec.hfz.entity.CmsLogLogin;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class CmsLogLoginService {

	@Autowired
	private CmsLogLoginMapper cmsLogLoginMapper;

	/**
	 * 查询（分页）
	 * 
	 * @param page
	 * @return
	 */
	public PageInfo<CmsLogLogin> selectAll(PageUtil page) {
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<CmsLogLogin> list = cmsLogLoginMapper.selectAll();
		PageInfo<CmsLogLogin> appsPageInfo = new PageInfo<>(list);
		return appsPageInfo;
	}

	/**
	 * 插入
	 * 
	 * @param obj
	 * @return
	 */
	public int insert(CmsLogLogin obj) {
		return cmsLogLoginMapper.insert(obj);
	}

}