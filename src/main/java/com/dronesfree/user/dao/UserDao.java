package com.dronesfree.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dronesfree.user.model.User;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private Transaction transaction;

	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	public static final String FIND_USER_BY_USERNAME = "from User as u where u.username = :parameter or u.email = :parameter ";
	public static final String FIND_USER_BY_ID = "from User as u where u.id = :id";

	@Override
	public User save(User user) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			return user;
		} catch (Exception e) {
			LOGGER.error("Couldn't save user. " + e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user = (User) session.createQuery(FIND_USER_BY_USERNAME)
					.setParameter("parameter", username).uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error("Couldn't find user by username '" + username + "'."
					+ e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return user;
	}

	// Remove user from database by email or username
	@Override
	public void removeUser(User user) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("Couldn't remove user from database"
					+ e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}

	// find user by id
	@Override
	public User getUserById(long id) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			User user = (User) session.createQuery(FIND_USER_BY_ID)
					.setParameter("id", id).uniqueResult();
			transaction.commit();
			return user;
		} catch (Exception e) {
			LOGGER.error("Couldn't find user by id '" + id + "'"
					+ e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return null;
	}

	@Override
	public User updateUser(User user) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return user;
	}
}
