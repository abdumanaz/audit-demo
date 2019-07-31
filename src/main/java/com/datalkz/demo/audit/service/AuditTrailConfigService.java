package com.datalkz.demo.audit.service;

import java.util.List;

import com.datalkz.demo.audit.bean.AuditBean;

public interface AuditTrailConfigService {

	public void writeAuditTrail(AuditBean auditBean);
	
	public List<AuditBean> getAuditTrail(AuditBean auditBean);
}
