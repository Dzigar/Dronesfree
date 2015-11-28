package com.dronesfree.common.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dronesfree.notification.service.INotificationService;
import com.dronesfree.order.service.IOrderService;

@Controller
public class AppController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private INotificationService notificationService;

	private final String HOMEPAGE = "index";

	// private final Logger LOGGER = Logger.getLogger(OrderDao.class);

	@RequestMapping(value = { "/", "/index", "/welcome" }, method = RequestMethod.GET)
	public String showHomePage(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		model.addAttribute("orders", orderService.getUncompletedOrders());
		// model.addAttribute("orderSeachForm", new OrderSeachForm());

		return HOMEPAGE;
	}
}