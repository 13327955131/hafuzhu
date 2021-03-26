package com.hoostec.hfz.service;

import com.hoostec.hfz.dto.LoginUser;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.entity.CmsMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	private CmsAdminService adminService;
	@Autowired
	private CmsMenuService cmsMenuService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CmsAdmin cmsAdmin = adminService.selectUserByName(username);
		if (cmsAdmin == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		}

		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(cmsAdmin, loginUser);

		List<CmsMenu> permissions = cmsMenuService.selectRoleMenus(cmsAdmin.getRoleId());
		loginUser.setPermissions(permissions);

		return loginUser;
	}
}
