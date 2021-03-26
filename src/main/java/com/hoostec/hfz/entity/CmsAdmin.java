package com.hoostec.hfz.entity;

import java.util.Date;

public class CmsAdmin {
	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像
	 */
	private String headImg;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 角色
	 */
	private Integer roleId;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 删除状态
	 */
	private Integer delStatus;

	/**
	 * 
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String remark;

	private String oldPassword;

	private Date lastLoginTime;

	private String lastLoginIp;

	private String type;

	private String phone;

	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * ID
	 *
	 * @return id ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * ID
	 *
	 * @param id
	 *            ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 名称
	 *
	 * @return username 名称
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 名称
	 *
	 * @param username
	 *            名称
	 */
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	/**
	 * 密码
	 *
	 * @return password 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 密码
	 *
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	/**
	 * 头像
	 *
	 * @return head_img 头像
	 */
	public String getHeadImg() {
		return headImg;
	}

	/**
	 * 头像
	 *
	 * @param headImg
	 *            头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg == null ? null : headImg.trim();
	}

	/**
	 * 邮箱
	 *
	 * @return email 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 邮箱
	 *
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * 角色
	 *
	 * @return role_id 角色
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * 角色
	 *
	 * @param roleId
	 *            角色
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 删除状态
	 *
	 * @return del_status 删除状态
	 */
	public Integer getDelStatus() {
		return delStatus;
	}

	/**
	 * 删除状态
	 *
	 * @param delStatus
	 *            删除状态
	 */
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	/**
	 *
	 * @return create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 *
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 备注
	 *
	 * @return remark 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 *
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}