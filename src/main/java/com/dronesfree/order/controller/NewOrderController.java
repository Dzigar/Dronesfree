package com.dronesfree.order.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dronesfree.location.model.Location;
import com.dronesfree.order.dto.OrderForm;
import com.dronesfree.order.service.IOrderService;
import com.dronesfree.user.service.IUserService;

@Controller
@RequestMapping(value = "/order")
public class NewOrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(NewOrderController.class);

	private final String NEW_ORDER_PAGE = "newOrderPage";

	private final String CREATE_NEW_ORDER_MESSAGE = "New order is created!";

	// Show page to add new order
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newOrderPage(ModelMap modelMap, Principal principal) {
		modelMap.addAttribute("newOrderForm", new OrderForm());
		return new ModelAndView(NEW_ORDER_PAGE);
	}

	// Create new order
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createNewOrder(Principal principal,
			@Valid OrderForm orderForm, HttpServletRequest request) {
		
		orderForm.setUser(userService.getUserByUsername(principal.getName()));
		orderService.createNewOrder(orderForm);
		LOGGER.info(CREATE_NEW_ORDER_MESSAGE);
		// redirect to user page
		return "redirect:/";
	}

	@RequestMapping(value = "/checkin", method = RequestMethod.POST, params = {
			"address", "latitude", "longitude" })
	public @ResponseBody String checkin(
			@RequestParam("address") String address,
			@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude,
			HttpServletRequest request, HttpServletResponse response,
			Principal principal) {
		Location location = new Location(latitude, longitude, address);
		orderService.checkin(
				userService.getUserByUsername(principal.getName()), location);
		LOGGER.info(CREATE_NEW_ORDER_MESSAGE);
		request.setAttribute("principal", principal);
		return "Hello";
	}
}
