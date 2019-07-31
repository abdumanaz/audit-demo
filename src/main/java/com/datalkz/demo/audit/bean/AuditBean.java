package com.datalkz.demo.audit.bean;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditBean {

	private Logger log = LoggerFactory.getLogger(AuditBean.class);
	
	private int auditId;
	private String userId;
	private String module;
	private String functionName;
	private String invocationDate;
	private List<Object> params;
	
	public AuditBean() {

	}

	public AuditBean(int auditId) {
		this(auditId, null, null, null, null, null);
	}

	public AuditBean(int auditId, String userId, String module, String functionName, String invocationDate,
			List<Object> params) {
		this.auditId = auditId;
		this.userId = userId;
		this.module = module;
		this.functionName = functionName;
		this.invocationDate = invocationDate;
		this.params = params;
	}

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getInvocationDate() {
		return invocationDate;
	}

	public void setInvocationDate(String invocationDate) {
		this.invocationDate = invocationDate;
	}

//	@JsonIgnore
	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}


	public JSONObject convertParamsToJson() {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject paramsJson = new JSONObject();
		this.params.forEach(param -> {
			try {
				String paramJsonAsString = mapper.writeValueAsString(param);
				paramsJson.put(param.getClass().getSimpleName(), paramJsonAsString);

			} catch (JsonProcessingException | JSONException e) {
				log.error("Error converting param: {} ", param.getClass().getSimpleName(), e );
			}

		});
		return paramsJson;
	}
	
	public List<Object> convertParamsToList(Clob clob) {
		List<Object> params = new ArrayList<>();
		try {
			JSONObject paramsJson = new JSONObject(clob.getSubString(1, (int) clob.length()));
//			auditBean.setParamsJson(params);
			Iterator<String> itr = paramsJson.keys();
			while( itr.hasNext()) {
				String key = itr.next();
				params.add(paramsJson.get(key));
			}
		} catch (JSONException | SQLException e) {
			log.error("Unable to parse params Object");
		}
		return params;
	}

}
