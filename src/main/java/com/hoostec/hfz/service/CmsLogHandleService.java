package com.hoostec.hfz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.dao.CmsLogHandleMapper;
import com.hoostec.hfz.entity.CmsLogHandle;
import com.hoostec.hfz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class CmsLogHandleService {

	@Autowired
	private CmsLogHandleMapper cmsLogHandleMapper;

	/**
	 * 查询（分页）
	 * 
	 * @param page
	 * @return
	 */
	public PageInfo<CmsLogHandle> selectAll(PageUtil page) {
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<CmsLogHandle> list = cmsLogHandleMapper.selectAll( );
		PageInfo<CmsLogHandle> appsPageInfo = new PageInfo<>(list);
		return appsPageInfo;
	}

	/**
	 * 插入
	 * 
	 * @param obj
	 * @return
	 */
	public int insert(CmsLogHandle obj) {
		return cmsLogHandleMapper.insert(obj);
	}

}