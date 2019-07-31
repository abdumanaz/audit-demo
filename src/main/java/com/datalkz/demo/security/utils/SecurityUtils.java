package com.datalkz.demo.security.utils;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.datalkz.demo.bean.ResponseBean;
import com.datalkz.demo.security.context.ThreadContext;
import com.datalkz.demo.user.context.UserContext;
import com.datalkz.demo.user.roles.UserRoles;

public class SecurityUtils {
	
	
	public static boolean checkUserRole(UserRoles role) {
		UserContext context = ThreadContext.getUserContext();
		if (context.getRole().equals(role)) {
			return true;
		}
		return false;
	}
	
	public static ResponseBean setUnAuthorizedStatus(HttpServletResponse response) {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		return new ResponseBean("User is not authorised to access the requested resource");
	}

}
