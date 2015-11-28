package com.dronesfree.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dronesfree.notification.service.INotificationService;

public class LocalInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private INotificationService notificationService;

	private final Logger LOGGER = Logger.getLogger(LocalInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		try {
//			modelAndView.addObject("notifications", notificationService
//					.getNewNotificationsByUsername(request.getUserPrincipal()
//							.getName()));
//		} catch (Exception e) {
//			LOGGER.error(e.getLocalizedMessage());
//		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
