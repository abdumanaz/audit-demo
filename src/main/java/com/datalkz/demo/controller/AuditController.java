package com.datalkz.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datalkz.demo.audit.bean.AuditBean;
import com.datalkz.demo.bean.ResponseBean;
import com.datalkz.demo.facade.AuditFacade;
import com.datalkz.demo.security.utils.SecurityUtils;
import com.datalkz.demo.user.roles.UserRoles;


@RestController
@RequestMapping("/audit")
public class AuditController {
	
	private Logger log = LoggerFactory.getLogger(AuditController.class);
	
	@Autowired
	AuditFacade auditFacade;
	
	@RequestMapping("/get")
	public ResponseBean getAuditTrail(HttpServletRequest request, HttpServletResponse response, @RequestBody AuditBean auditBean) {
		log.info("Get Audit Trail invoked");
		boolean canUserAccess = SecurityUtils.checkUserRole(UserRoles.ADMIN);
		if (!canUserAccess) {
			log.error("User is not authorized to access the particular resource");
			 ResponseBean responseBean = SecurityUtils.setUnAuthorizedStatus(response);
			 
			 return responseBean;
		}
		return this.auditFacade.getAuditTrail(request, auditBean);
		
	}
}
