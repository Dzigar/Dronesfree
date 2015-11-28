package com.dronesfree.notification.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dronesfree.notification.model.Notification;
import com.dronesfree.order.dao.OrderDao;
import com.dronesfree.user.model.User;

@Repository
public class NotificationDao implements INotificationDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private static final Logger LOGGER = Logger.getLogger(OrderDao.class);

	@Override
	public void saveNewNotification(Notification notification) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(notification);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateNotification(Notification notification) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(notification);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Notification getNotificationById(Long notificationId) {
		Notification notification = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.getTransaction();
			notification = (Notification) session
					.createQuery(
							"from Notification n where n.id = :notificationId")
					.setParameter("notificationId", notificationId)
					.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return notification;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getAllUserNotifications(User user) {
		List<Notification> notifications = new ArrayList<Notification>();
		try {
			session = sessionFactory.openSession();
			transaction = session.getTransaction();
			notifications = session
					.createQuery("From Notification n where n.user = :user")
					.setParameter("user", user).list();
			if (!transaction.wasCommitted())
				transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return notifications;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getNewUserNotifications(User user) {
		List<Notification> notifications = new ArrayList<Notification>();
		try {
			session = sessionFactory.openSession();
			transaction = session.getTransaction();
			notifications = session
					.createQuery(
							"From Notification n where n.user = :user and n.revised = false")
					.setParameter("user", user).list();
			if (!transaction.wasCommitted())
				transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return notifications;
	}

	@Override
	public void deleteNotification(Notification notification) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery(
					"delete Notification as n where n.id = :notificationId")
					.setParameter("notificationId", notification.getId())
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
