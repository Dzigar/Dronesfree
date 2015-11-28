package com.dronesfree.location.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dronesfree.location.model.Location;

@Repository
public class LocationDao implements ILocationDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private final String DELETE_LOCATION_QUERY = "delete Location as l where l.id = :locationId";

	private final Logger LOGGER = Logger.getLogger(LocationDao.class);

	@Override
	public void saveLocation(Location location) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(location);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void removeLocation(Location location) {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery(DELETE_LOCATION_QUERY)
					.setParameter("locationId", location.getId())
					.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("Could not remove location.  Exception "
					+ e.getLocalizedMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
