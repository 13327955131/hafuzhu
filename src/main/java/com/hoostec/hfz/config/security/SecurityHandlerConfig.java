package com.hoostec.hfz.config.security;

import com.hoostec.hfz.config.filter.TokenFilter;
import com.hoostec.hfz.dto.LoginUser;
import com.hoostec.hfz.dto.Token;
import com.hoostec.hfz.entity.CmsMenu;
import com.hoostec.hfz.service.TokenService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.ResultDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring security处理器
 *
 * @author 小Loo
 */
@Configuration
public class SecurityHandlerConfig {

	@Autowired
	private TokenService tokenService;

	/**
	 * 登陆成功，返回Token
	 *
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				LoginUser loginUser = (LoginUser) authentication.getPrincipal();
				Map<String, Object> resultMap = new HashMap<String, Object>();
				// 保存token
				Token token = tokenService.saveToken(loginUser);
				// 菜单权限
				List<CmsMenu> menuList = treeList(loginUser.getPermissions(), 0);
				// 按钮权限
				List<String> sList = new ArrayList<String>();
				for (GrantedAuthority grantedAuthority : loginUser.getAuthorities()) {
					sList.add(grantedAuthority.getAuthority());
				}
				Object[] btnList = sList.toArray();
				resultMap.put("menuList", menuList);
				resultMap.put("btnList", btnList);
				resultMap.put("token", token);
				ResultDataUtil.isOkResponse(resultMap, response);
			}
		};
	}

	/**
	 * 登陆失败
	 *
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				String msg = null;
				if (exception instanceof BadCredentialsException) {
					msg = "密码错误";
				} else {
					msg = exception.getMessage();
				}
				ResultDataUtil.isErrorResponse(Constant.ERROR_CODE_1002, msg, response);
			}
		};

	}

	/**
	 * 树形数据
	 *
	 * @param list
	 * @param parentId
	 * @return
	 */
	public static List<CmsMenu> treeList(List<CmsMenu> list, int parentId) {
		List<CmsMenu> resultList = new ArrayList<CmsMenu>();
		for (CmsMenu menu : list) {
			if (menu.getType() != 2) {
				if (menu.getParentId() == parentId) {
					List<CmsMenu> sList = treeList(list, menu.getId());
					menu.setChildren(sList);
					resultList.add(menu);
				}
			}

		}
		return resultList;
	}

	/**
	 * 未登录，返回401
	 *
	 * @return
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				ResultDataUtil.isErrorResponse(Constant.ERROR_CODE_1001, "token不合法或者过期！", response);
			}
		};
	}

	/**
	 * 退出处理
	 *
	 * @return
	 */
	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				String token = TokenFilter.getToken(request);
				tokenService.deleteToken(token);
				ResultDataUtil.isOkResponse("退出成功！", response);
			}
		};
	}
}
