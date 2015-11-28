package com.dronesfree.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dronesfree.order.service.IOrderService;
import com.dronesfree.proposal.service.IProposalService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IProposalService proposalService;

	private final String ORDER_PAGE = "orderPage";

	@RequestMapping(params = "id", method = RequestMethod.GET)
	public ModelAndView viewContractPage(
			@ModelAttribute("errorMessage") String errorMessage,
			@RequestParam("id") long orderId, Model model,
			HttpServletRequest request) {

		model.addAttribute("order", orderService.getOrderById(orderId));
		model.addAttribute("proposals",
				proposalService.getProposalsByOrderId(orderId));
		model.addAttribute("errorMessage", errorMessage);
		return new ModelAndView(ORDER_PAGE);
	}

}
