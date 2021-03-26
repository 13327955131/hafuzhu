package com.hoostec.hfz.controller.back;

import com.github.pagehelper.PageInfo;
import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsMenu;
import com.hoostec.hfz.entity.CmsRole;
import com.hoostec.hfz.entity.CmsRoleMenu;
import com.hoostec.hfz.service.CmsMenuService;
import com.hoostec.hfz.service.CmsRoleMenuService;
import com.hoostec.hfz.service.CmsRoleService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.PageUtil;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author loo
 * @version 1.0.0
 */
@RestController
@RequestMapping("cms/role")
public class CmsRoleController {
	@Autowired
	private CmsRoleService cmsRoleService;
	@Autowired
	private CmsRoleMenuService cmsRoleMenuService;
	@Autowired
	private CmsMenuService cmsMenuService;

	/**
	 * 角色列表
	 *
	 * @param cmsRole
	 * @return
	 */
	@PreAuthorize("hasAuthority('role:list')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResultDataUtil menuList(CmsRole cmsRole, PageUtil page) {
		cmsRole.setDelStatus(Constant.DEL_STATUS_ONE);
		PageInfo<CmsRole> list = cmsRoleService.selectAll(cmsRole, page);
		return ResultDataUtil.isOkJsonList(list.getTotal(), list.getList());
	}

	/**
	 * 角色删除
	 *
	 * @param request
	 * @return
	 */
	@MyLog("角色删除")
	@PreAuthorize("hasAuthority('role:delete')")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultDataUtil menuDelete(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids[]");
		return ResultDataUtil.isOk(cmsRoleService.deleteAll(ids));
	}

	/**
	 * 角色新增
	 *
	 * @param cmsRole
	 * @return
	 */
	@MyLog("角色新增")
	@PreAuthorize("hasAuthority('role:add')")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultDataUtil menuAdd(CmsRole cmsRole) {
		return ResultDataUtil.isOk(cmsRoleService.insert(cmsRole));
	}

	/**
	 * 角色更新
	 *
	 * @param request
	 * @return
	 */
	@MyLog("角色更新")
	@PreAuthorize("hasAuthority('role:update')")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@Transactional
	public ResultDataUtil menuUpdate(CmsRole cmsRole, HttpServletRequest request) {

		String[] authIds = request.getParameterValues("authIds[]");

		// 更新角色信息
		cmsRoleService.update(cmsRole);
		CmsRoleMenu cmsRoleMenu = new CmsRoleMenu();
		cmsRoleMenu.setRoleId(cmsRole.getId());
		// 删除角色权限
		cmsRoleMenuService.deleteAllByRole(cmsRole.getId());
		// 设置权限
		cmsRoleMenu.setAuthIds(authIds);
		// 批量插入角色权限
		cmsRoleMenuService.insertAllByRole(cmsRoleMenu);

		return ResultDataUtil.isOk(null);
	}

	/**
	 * 菜单列表
	 *
	 * @param cmsMenu
	 * @return
	 */
	@PreAuthorize("hasAuthority('menu:list')")
	@RequestMapping(value = "/menu/list", method = RequestMethod.GET)
	public ResultDataUtil getAll(Integer roleId, PageUtil page) {
		CmsMenu cmsMenu = new CmsMenu();
		cmsMenu.setDelStatus(Constant.DEL_STATUS_ONE);
		List<CmsMenu> list = cmsMenuService.selectAll(cmsMenu);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("checkedId", cmsRoleMenuService.selectAuthIds(roleId));

		return ResultDataUtil.isOk(result);
	}
}
