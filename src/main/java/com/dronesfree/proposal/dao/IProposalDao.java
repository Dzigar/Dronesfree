package com.dronesfree.proposal.dao;

import java.util.List;

import com.dronesfree.order.model.Order;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.user.model.User;

public interface IProposalDao {

	Proposal saveProposal(Proposal proposal);

	Proposal updateProposal(Proposal proposal);

	Proposal getProposalById(Long proposalId);

	List<Proposal> getOrderProposals(Order order);

	List<Proposal> getUserProposals(User user);

	void deleteProposal(Proposal proposal);
}
