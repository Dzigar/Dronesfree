package com.dronesfree.proposal.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.notification.model.NotificationType;
import com.dronesfree.notification.service.INotificationService;
import com.dronesfree.order.model.Order;
import com.dronesfree.order.model.OrderStatus;
import com.dronesfree.order.service.IOrderService;
import com.dronesfree.proposal.dao.IProposalDao;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.user.service.IUserService;

@Service
@Transactional
public class ProposalService implements IProposalService {

	@Autowired
	private IProposalDao proposalDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private INotificationService notificationService;

	@Override
	public void createProposal(Long orderId, String userName)
			throws ProposalCreationException {

		// Create and save proposal
		Proposal proposal = Proposal.getBuilder()
				.user(userService.getUserByUsername(userName)).build();
		proposalDao.saveProposal(proposal);

		Order order = orderService.getOrderById(orderId);
		if (proposal.getUser().equals(order.getUser())) {
			throw new ProposalCreationException(
					"You can not accept order which you created!");
		}
		if (order.containsProposal(proposal)) {
			throw new ProposalCreationException(
					"You've already created a proposal for the order!");
		}

		proposal.setOrder(order);
		proposalDao.updateProposal(proposal);

		// Create notification about create new proposal
		notificationService.createNewNotification(proposal,
				NotificationType.CREATE_PROPOSAL);

	}

	@Override
	public List<Proposal> getProposalsByOrderId(Long orderId) {
		return proposalDao
				.getOrderProposals(orderService.getOrderById(orderId));
	}

	@Override
	public List<Proposal> getProposalsByUserName(String userName) {
		return proposalDao.getUserProposals(userService
				.getUserByUsername(userName));
	}

	@Override
	public void deleteOrderProposals(Order order) {
		Iterator<Proposal> proposals = order.getProposals().iterator();
		while (proposals.hasNext()) {
			Proposal proposal = proposals.next();
			notificationService.deleteNotification(proposal.getNotification());
			proposalDao.deleteProposal(proposal);
		}
	}

	@Override
	public Proposal getProposalById(Long proposalId) {
		return proposalDao.getProposalById(proposalId);
	}

	@Override
	public void acceptProposal(Proposal proposal) {

		// Assign performer for order
		Order order = proposal.getOrder();
		order.setPerformer(proposal.getUser());
		// After the appointment of performer change order status from 'NEW' to
		// 'PERFORMED'
		order.setStatus(OrderStatus.PERFORMED);
		orderService.updateOrder(order);

		proposal.setAccepted(true);
		proposalDao.updateProposal(proposal);

		// Create notification about appointment of performer
		notificationService.createNewNotification(proposal,
				NotificationType.ACCEPT_PROPOSAL);
	}

	@Override
	public void deleteProposal(Proposal proposal) {
		proposalDao.deleteProposal(proposal);
	}

}
