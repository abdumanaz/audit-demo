package com.datalkz.demo.security.context;

import com.datalkz.demo.user.context.UserContext;

public class ThreadContext {

	public static ThreadLocal<UserContext> local = new ThreadLocal<>();

	public static UserContext getUserContext() {
		return local.get();
	}

	public static void setUserContext(UserContext context) {
		local.set(context);
	}
}
