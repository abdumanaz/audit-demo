package com.datalkz.demo.audit.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.datalkz.demo.audit.annotation.AuditableBean;
import com.datalkz.demo.audit.annotation.AuditableService;
import com.datalkz.demo.audit.bean.AuditBean;
import com.datalkz.demo.audit.service.AuditTrailConfigService;
import com.datalkz.demo.security.context.ThreadContext;
import com.datalkz.demo.user.context.UserContext;

@Aspect
@Configuration
public class AuditTrailConfig {

	@Autowired
	AuditTrailConfigService auditTrailConfigService;

	private Logger log = LoggerFactory.getLogger(AuditTrailConfig.class);

	@Pointcut("within(@com.datalkz.demo.audit.annotation.AuditableService *)")
	public void auditableServices() {
	}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
	}
	
	@Pointcut("@annotation(com.datalkz.demo.audit.annotation.ExcludeFromAudit)")
	public void nonAuditableMthods() {
	}

	@Pointcut("auditableServices() && publicMethod() && !nonAuditableMthods()")
	public void auditableMethods() {
	}

//	@Before("auditableMethods()")
//	public void before(JoinPoint joinPoint) {
//
//		Object bean = joinPoint.getTarget();
//		AuditBean auditBean = new AuditBean();
//		auditBean.setModule(this.getModule(bean));
//		auditBean.setUserId(this.getUserId());
//		auditBean.setFunctionName(joinPoint.getSignature().getName());
//		auditBean.setParams(this.getAuditableParams(joinPoint.getArgs()));
//		this.auditTrailConfigService.writeAuditTrail(auditBean);
//
//	}
	
	
	@Around("auditableMethods()")
	public Object aroundAuditableMethods(ProceedingJoinPoint joinPoint) {

		Object bean = joinPoint.getTarget();
		AuditBean auditBean = new AuditBean();
		auditBean.setModule(this.getModule(bean));
		auditBean.setUserId(this.getUserId());
		auditBean.setFunctionName(joinPoint.getSignature().getName());
		auditBean.setParams(this.getAuditableParams(joinPoint.getArgs()));
		Object result;
		try {
			result = joinPoint.proceed(joinPoint.getArgs());
		} catch (Throwable e) {
			log.error("Error in invoking method : {}", auditBean.getFunctionName());
			return null;
		}
		
		
		this.auditTrailConfigService.writeAuditTrail(auditBean);
		return result;

	}

	private String getModule(Object bean) {
		AuditableService annotationObj = bean.getClass().getAnnotation(AuditableService.class);
		return annotationObj.module();
	}

	private String getUserId() {
		UserContext userContext = ThreadContext.getUserContext();
		return userContext.getUserId();
	}

	private List<Object> getAuditableParams(Object[] params) {
		List<Object> auditableParams = new ArrayList<Object>();

		if (params != null) {

			Arrays.stream(params).forEach(param -> {
				AuditableBean auditableBean = param.getClass().getAnnotation(AuditableBean.class);
				if (auditableBean == null) {
					return;
				}
				auditableParams.add(param);
			});

		}
		return auditableParams;
	}

}
