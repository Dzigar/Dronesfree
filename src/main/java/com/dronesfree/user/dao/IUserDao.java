package com.dronesfree.user.dao;

import com.dronesfree.user.model.User;

public interface IUserDao {

	public User save(User user);

	public User getUserByUsername(String parameter);

	public User getUserById(long id);

	public void removeUser(User user);

	public User updateUser(User user);
}
