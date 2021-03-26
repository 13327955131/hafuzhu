package com.hoostec.hfz.controller.back;

import com.hoostec.hfz.config.log.MyLog;
import com.hoostec.hfz.entity.CmsAdmin;
import com.hoostec.hfz.service.CmsAdminService;
import com.hoostec.hfz.utils.Constant;
import com.hoostec.hfz.utils.ResultDataUtil;
import com.hoostec.hfz.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common")
public class CmsCommonController {

	@Autowired
	private CmsAdminService cmsAdminService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("user/info")
	public ResultDataUtil getUserInfo() {
		return ResultDataUtil.isOk(cmsAdminService.selectOne(UserUtil.getLoginUser().getId()));
	}

	@MyLog("信息修改")
	@RequestMapping("user/info/update")
	public ResultDataUtil infoUpdate(CmsAdmin user) {
		user.setId(UserUtil.getLoginUser().getId());
		cmsAdminService.update(user);
		return ResultDataUtil.isOk(null);
	}

	@MyLog("密码修改")
	@RequestMapping("user/pwd/update")
	public ResultDataUtil pwdUpdate(CmsAdmin user) {
		CmsAdmin cmsAdmin = cmsAdminService.selectOne(UserUtil.getLoginUser().getId());
		if (cmsAdmin == null) {
			return ResultDataUtil.isError(Constant.ERROR_CODE_1004, "账号不存在！");
		}

		if (!passwordEncoder.matches(user.getOldPassword(), cmsAdmin.getPassword())) {
			return ResultDataUtil.isError(Constant.ERROR_CODE_1002, "原密码错误！");
		} else {
			user.setId(UserUtil.getLoginUser().getId());
			cmsAdminService.update(user);
			return ResultDataUtil.isOk(null);
		}

	}
}
