package com.dronesfree.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.location.dao.ILocationDao;
import com.dronesfree.location.model.Location;

@Service
@Transactional
public class LocationService implements ILocationService {

	@Autowired
	private ILocationDao locationDao;

	@Override
	public void saveNewLocation(Location location) {
		locationDao.saveLocation(location);
	}

	@Override
	public void removeLocation(Location location) {
		locationDao.removeLocation(location);
	}

}
