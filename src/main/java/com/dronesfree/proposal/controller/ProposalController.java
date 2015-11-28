package com.dronesfree.proposal.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dronesfree.order.service.IOrderService;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.proposal.service.IProposalService;
import com.dronesfree.proposal.service.ProposalCreationException;

@Controller
@RequestMapping(value = "/proposal")
public class ProposalController {

	@Autowired
	private IProposalService proposalService;

	@Autowired
	private IOrderService orderService;

	private static final Logger log = LoggerFactory
			.getLogger(ProposalController.class);

	@RequestMapping(value = "/create", params = "id", method = RequestMethod.POST)
	public ModelAndView acceptOrder(@RequestParam("id") long orderId,
			Principal principal) {

		ModelAndView modelAndView = new ModelAndView("redirect:/order?id="
				+ orderId);
		try {
			proposalService.createProposal(orderId, principal.getName());
		} catch (ProposalCreationException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			log.error(e.getLocalizedMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = { "id" })
	public String checkin(@RequestParam("id") Long proposalId, Model model) {
		Proposal proposal = proposalService.getProposalById(proposalId);
		try {
			proposalService.acceptProposal(proposal);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			log.error(e.getLocalizedMessage());
		}
		return "redirect:/order?id=" + proposal.getOrder().getId();
	}
}
