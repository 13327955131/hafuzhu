package com.hoostec.hfz.entity;

public class CmsRoleMenu {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 菜单id
	 */
	private Integer menuId;

	/**
	 * 角色id
	 */
	private Integer roleId;

	private String[] authIds;

	/**
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 菜单id
	 * 
	 * @return menu_id 菜单id
	 */
	public Integer getMenuId() {
		return menuId;
	}

	/**
	 * 菜单id
	 * 
	 * @param menuId
	 *            菜单id
	 */
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	/**
	 * 角色id
	 * 
	 * @return role_id 角色id
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * 角色id
	 * 
	 * @param roleId
	 *            角色id
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String[] getAuthIds() {
		return authIds;
	}

	public void setAuthIds(String[] authIds) {
		this.authIds = authIds;
	}
}