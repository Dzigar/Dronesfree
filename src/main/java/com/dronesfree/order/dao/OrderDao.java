package com.dronesfree.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dronesfree.order.model.Order;
import com.dronesfree.user.model.User;

@Repository
public class OrderDao implements IOrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private static final Logger LOGGER = Logger.getLogger(OrderDao.class);

	public static final String FIND_ORDER_BY_PARAMS = "from Order as o where o.price <= :price"
			+ " and o.isBid = :isBid";

	public static final String FIND_ORDER_BY_ID = "from Order as o where o.id = :id";

	@Override
	public void saveOrder(Order order) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			orders = session.createQuery("From Order").list();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return orders;
	}

	@Override
	public void removeOrder(Order order) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createQuery("delete Order as o where o.id = :orderId")
					.setParameter("orderId", order.getId()).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}

	@Override
	public Order getOrderById(Long id) {
		Order order = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			order = (Order) session.createQuery(FIND_ORDER_BY_ID)
					.setParameter("id", id).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getUserOrders(User user) {
		List<Order> orders = new ArrayList<Order>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			orders = session.createQuery("from Order o where o.user = :user")
					.setParameter("user", user).list();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return orders;
	}

	@Override
	public void updateOrder(Order order) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(order);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getUncompletedOrders() {
		List<Order> orders = new ArrayList<Order>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			orders = session.createQuery("From Order o where o.status != 'COMPLETED'").list();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return orders;
	}
}
