package com.datalkz.demo.audit.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.datalkz.demo.audit.bean.AuditBean;
import com.datalkz.demo.controller.ProductController;

@Repository
public class AuditTrailConfigDao extends JdbcDaoSupport {

	private Logger log = LoggerFactory.getLogger(AuditTrailConfigDao.class);
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		this.setDataSource(dataSource);
	}
	
	private String INSERT_STATEMENT = " " +
			"INSERT 					"
			+ "INTO   AUDIT_TRAIL("
			+ "		  AUDIT_ID,			"
			+ "		  USER_ID,			"
			+ "		  MODULE,			"
			+ "		  FUNCTION_NAME,	"
			+ "		  INVOCATION_DATE,"
			+ "		  PARAMS)"
			+ ""
			+ "VALUES(			"
			+ "		 AUDIT_TRAIL_ID_SEQ.nextVal, ?, ?, ?, ?, ?)";

	public void writeAuditTrail(AuditBean auditBean) {

		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(INSERT_STATEMENT);
				ps.setString(1, auditBean.getUserId());
				ps.setString(2, auditBean.getModule());
				ps.setString(3, auditBean.getFunctionName());
				ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
				Clob paramClob = con.createClob();
				paramClob.setString(1, auditBean.convertParamsToJson().toString());
				ps.setClob(5, paramClob);
				return ps;
			}
		});
		
	}
	
	private String GET_AUDIT = " SELECT AUDIT_ID, USER_ID, MODULE, FUNCTION_NAME, INVOCATION_DATE, PARAMS FROM AUDIT_TRAIL ";
	private String WHERE = " WHERE ";
	private String AND = " AND ";
	
	public List<AuditBean> getAuditTrail(AuditBean auditBean) {
		
		StringBuilder query = new StringBuilder(GET_AUDIT);
		List<Object> params = new ArrayList<Object>();
		boolean firstClause = true;
		
		String userId = auditBean.getUserId();
		String module = auditBean.getModule();
		String functionName = auditBean.getFunctionName();
		String invocationDate = auditBean.getInvocationDate();
		
		if (auditBean.getAuditId() != 0) {
			if (firstClause) {
				query.append(WHERE);
				firstClause = false;
			} else {
				query.append(AND);
			}
			params.add(auditBean.getAuditId());
			query.append( " AUDIT_ID = ? ");
		}
		
		
		
		if (userId != null && !"".equals(userId)) {
			if (firstClause) {
				query.append(WHERE);
				firstClause = false;
			} else {
				query.append(AND);
			}
			params.add(userId);
			query.append( " USER_ID = ? ");
		}
		
		if (module != null && !"".equals(module)) {
			if (firstClause) {
				query.append(WHERE);
				firstClause = false;
			} else {
				query.append(AND);
			}
			params.add(module);
			query.append( " MODULE = ? ");
		}
		
		if (functionName != null && !"".equals(functionName)) {
			if (firstClause) {
				query.append(WHERE);
				firstClause = false;
			} else {
				query.append(AND);
			}
			params.add(functionName);
			query.append( " FUNCTION_NAME = ? ");
		}
		
		
		if (invocationDate != null && !"".equals(invocationDate)) {
			if (firstClause) {
				query.append(WHERE);
				firstClause = false;
			} else {
				query.append(AND);
			}
			params.add(invocationDate);
			query.append( " TO_CHAR(INVOCATION_DATE, 'DD-MM-YYYY') = ? ");
		}

		return this.getJdbcTemplate().query(query.toString(), params.toArray(), new RowMapper<AuditBean>() {

			@Override
			public AuditBean mapRow(ResultSet rs, int paramInt) throws SQLException {
				AuditBean auditBean = new AuditBean();
				auditBean.setAuditId(rs.getInt(1));
				auditBean.setUserId(rs.getString(2));
				auditBean.setModule(rs.getString(3));
				auditBean.setFunctionName(rs.getString(4));
				auditBean.setInvocationDate(rs.getString(5));
				Clob clob = rs.getClob(6);
				if (clob != null) {
					auditBean.setParams(auditBean.convertParamsToList(clob));
				}
				
				return auditBean;
				
			}
		});
		
	}
	
	
}
