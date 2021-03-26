package com.hoostec.hfz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.entity.CmsMenu;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser extends CmsAdmin implements UserDetails {

	private static final long serialVersionUID = -1379274258881257107L;

	/**
	 * 权限
	 */
	private List<CmsMenu> permissions;

	/**
	 * token
	 */
	private String token;

	/**
	 * 登陆时间戳（毫秒）
	 */
	private Long loginTime;

	/**
	 * 过期时间戳
	 */
	private Long expireTime;

	public List<CmsMenu> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<CmsMenu> permissions) {
		this.permissions = permissions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getRoute()))
				.map(p -> new SimpleGrantedAuthority(p.getRoute())).collect(Collectors.toSet());
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// do nothing
	}

	// 账户是否未过期
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 密码是否未过期
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 账户是否激活
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}
}
