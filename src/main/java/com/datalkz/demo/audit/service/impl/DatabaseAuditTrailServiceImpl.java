package com.datalkz.demo.audit.service.impl;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datalkz.demo.audit.bean.AuditBean;
import com.datalkz.demo.audit.dao.AuditTrailConfigDao;
import com.datalkz.demo.audit.service.AuditTrailConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("auditTrailConfigService")
public class DatabaseAuditTrailServiceImpl implements AuditTrailConfigService {

	private Logger log = LoggerFactory.getLogger(DatabaseAuditTrailServiceImpl.class);

	@Autowired
	AuditTrailConfigDao auditTrailConfigDao;
	
	@Override
	public void writeAuditTrail(AuditBean auditBean) {
		
		this.auditTrailConfigDao.writeAuditTrail(auditBean);
	}

	@Override
	public List<AuditBean> getAuditTrail(AuditBean auditBean) {
		return this.auditTrailConfigDao.getAuditTrail(auditBean);
		
	}

}
