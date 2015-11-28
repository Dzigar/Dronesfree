package com.dronesfree.proposal.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dronesfree.order.dao.OrderDao;
import com.dronesfree.order.model.Order;
import com.dronesfree.proposal.model.Proposal;
import com.dronesfree.user.model.User;

@Repository
public class ProposalDao implements IProposalDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private static final Logger LOGGER = Logger.getLogger(OrderDao.class);

	@Override
	public Proposal saveProposal(Proposal proposal) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(proposal);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return proposal;

	}

	@Override
	public Proposal getProposalById(Long proposalId) {
		Proposal proposal = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			proposal = (Proposal) session
					.createQuery("From Proposal p where p.id = :proposalId")
					.setParameter("proposalId", proposalId).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return proposal;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Proposal> getOrderProposals(Order order) {
		List<Proposal> proposals = new ArrayList<Proposal>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			proposals = session
					.createQuery("From Proposal p where p.order = :order")
					.setParameter("order", order).list();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return proposals;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Proposal> getUserProposals(User user) {
		List<Proposal> proposals = new ArrayList<Proposal>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			proposals = session
					.createQuery("From Proposal p where p.user = :user")
					.setParameter("user", user).list();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return proposals;
	}

	@Override
	public Proposal updateProposal(Proposal proposal) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(proposal);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return proposal;
	}

	@Override
	public void deleteProposal(Proposal proposal) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete Proposal as p where p.id = :proposalId")
					.setParameter("proposalId", proposal.getId())
					.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

}
