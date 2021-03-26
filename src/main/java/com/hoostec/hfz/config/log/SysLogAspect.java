package com.hoostec.hfz.config.log;

import com.hoostec.hfz.entity.CmsLogHandle;
import com.hoostec.hfz.service.CmsLogHandleService;
import com.hoostec.hfz.utils.DataUtils;
import com.hoostec.hfz.utils.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class SysLogAspect {

	@Autowired
	private CmsLogHandleService sysLogService;

	// 定义切点 @Pointcut
	// 在注解的位置切入代码
	@Pointcut("@annotation(com.hoostec.hfz.config.log.MyLog)")
	public void logPoinCut() {
	}

	// 切面 配置通知
	@AfterReturning("logPoinCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		// 保存日志
		CmsLogHandle sysLog = new CmsLogHandle();

		// 从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// 获取切入点所在的方法
		Method method = signature.getMethod();
		// 获取操作
		MyLog myLog = method.getAnnotation(MyLog.class);
		if (myLog != null) {
			String value = myLog.value();
			sysLog.setOperation(value);// 保存获取的操作
		}
		// 获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 获取请求的方法名
		String methodName = method.getName();
		sysLog.setMethod(className + "." + methodName);
		// 获取用户名
		sysLog.setUsername(UserUtil.getLoginUser().getNickName());
		// 获取用户ip地址
		sysLog.setIp(DataUtils.getIp());

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 参数
		sysLog.setParam(DataUtils.getQueryString(request));
		// 请求地址
		sysLog.setUrl(request.getServletPath());
		// 调用service保存数据库
		sysLogService.insert(sysLog);
	}
}
