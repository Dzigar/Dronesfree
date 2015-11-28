package com.dronesfree.proposal.service;

import java.util.List;

import com.dronesfree.order.model.Order;
import com.dronesfree.proposal.model.Proposal;

public interface IProposalService {

	void createProposal(Long orderId, String userName) throws ProposalCreationException;

	Proposal getProposalById(Long proposalId);
	
	List<Proposal> getProposalsByOrderId(Long orderId);

	List<Proposal> getProposalsByUserName(String userName);

	void deleteProposal(Proposal proposal);
	
	void deleteOrderProposals(Order order);
	
	void acceptProposal(Proposal proposal);
}
