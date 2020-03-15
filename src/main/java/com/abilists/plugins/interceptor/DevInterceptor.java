package com.abilists.plugins.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.abilists.bean.model.account.UsersModel;
import com.abilists.plugins.interceptor.PluginsInterceptor;

public class DevInterceptor implements PluginsInterceptor {

	final Logger logger = LoggerFactory.getLogger(DevInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		UsersModel user = new UsersModel();
		user.setUserId("admin");
		user.setUserNo(1);
		user.setUserName("Developer");

		request.getSession().setAttribute("user", user);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
