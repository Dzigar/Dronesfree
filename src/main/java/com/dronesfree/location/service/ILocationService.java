package com.dronesfree.location.service;

import com.dronesfree.location.model.Location;

public interface ILocationService {

	public void saveNewLocation(Location location);

	public void removeLocation(Location location);

}
