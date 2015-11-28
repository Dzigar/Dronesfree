package com.dronesfree.order.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dronesfree.order.service.IOrderService;

@Controller
@RequestMapping(value = "/order")
public class RemoveOrderController {

	@Autowired
	private IOrderService orderService;

	private final Logger LOGGER = Logger.getLogger(OrderController.class);

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
	public String getOrderFromService(@PathVariable long id, Principal principal) {
		orderService.removeOrderById(id);
		LOGGER.info("Order " + id + " has been deleted");
		return "redirect:/" + principal.getName();
	}
}
