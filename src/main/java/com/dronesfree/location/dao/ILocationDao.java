package com.dronesfree.location.dao;

import com.dronesfree.location.model.Location;

public interface ILocationDao {

	public void saveLocation(Location location);

	public void removeLocation(Location location);

}
