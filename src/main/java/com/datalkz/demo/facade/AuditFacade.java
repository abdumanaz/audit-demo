package com.datalkz.demo.facade;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datalkz.demo.audit.bean.AuditBean;
import com.datalkz.demo.audit.service.AuditTrailConfigService;
import com.datalkz.demo.bean.ResponseBean;

@Service
public class AuditFacade {

	@Autowired
	AuditTrailConfigService auditTrailConfigService;
	
	public ResponseBean getAuditTrail(HttpServletRequest request, AuditBean auditBean) {
		ResponseBean bean = new ResponseBean();
		bean.setData(this.auditTrailConfigService.getAuditTrail(auditBean));
		return bean;
	}

}
