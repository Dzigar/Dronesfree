package com.dronesfree.notification.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dronesfree.notification.service.INotificationService;

@Controller
@RequestMapping(value = "/notifications")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	private final String NOTIFICATIONS_PAGE = "notificationsPage";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showNotificationsPage(Model model, Principal principal) {
		ModelAndView modelAndView = new ModelAndView(NOTIFICATIONS_PAGE);
		model.addAttribute("notifs", notificationService
				.getNotificationByUsername(principal.getName()));
		return modelAndView;
	}

}
