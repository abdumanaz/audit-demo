package com.datalkz.demo.security.filter;

import static com.datalkz.demo.security.constants.SecurityConstants.AUTHORIZATION_HEADER;
import static com.datalkz.demo.security.constants.SecurityConstants.HEADER_PREFIX;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.datalkz.demo.security.context.ThreadContext;
import com.datalkz.demo.user.context.UserContext;
import com.datalkz.demo.user.roles.UserRoles;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter(value="/*" )
public class SecurityFilter implements Filter {

	private Logger log = LoggerFactory.getLogger(SecurityFilter.class);
	

	private String excludedPaths = "/login.json";

	public void init(FilterConfig fConfig) throws ServletException {
		  log.info("SecurityFilter initialized. Url Patterns: /*");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (excludedPaths.equals(req.getRequestURI())) {
		
			chain.doFilter(request, response);
		} else {
			System.out.println(req.getRequestURI());
			String authHeader = req.getHeader(AUTHORIZATION_HEADER);
			if (authHeader == null || "".equals(authHeader)) {
				log.error("User is not authorized");
				this.setUnAuthorizedStatus(resp);
				return;
			}
			
			String bearerToken = authHeader.substring(HEADER_PREFIX.length() + 1);
			
			if (bearerToken == null || "".equals(bearerToken.trim())) {
				log.error("User is not authorized");
				this.setUnAuthorizedStatus(resp);
				return;
			}
			
			// fetch USer Details
			// Hardcoding for now
			String userName;
			String userId;
			UserRoles role;
			if ("ADMIN".equalsIgnoreCase(bearerToken)) {
				userName = "ADMIN";
				userId = "ADM01";
				role = UserRoles.ADMIN;
			} else {
				userName = "NORMAL";
				userId = "USER02";
				role = UserRoles.NORMAL;
			}
			
			UserContext userContext = new UserContext(userName, userId, role);
			ThreadContext.setUserContext(userContext);
			chain.doFilter(request, response);
		}
		
		
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	private void setUnAuthorizedStatus(HttpServletResponse response) {
		this.setResponseStatus(response, HttpStatus.UNAUTHORIZED);
	}
	
	private void setResponseStatus(HttpServletResponse response, HttpStatus status) {
		response.setStatus(status.value());
	}



}
