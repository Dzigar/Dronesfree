package com.dronesfree.user.service;

import com.dronesfree.user.dto.RegistrationForm;
import com.dronesfree.user.model.User;

public interface IUserService {

	public User registerNewUserAccount(RegistrationForm userAccountData)
			throws DuplicateEmailException;

	public User getUserByUsername(String username);

	public User getUserById(long id);

	public void removeUserProfile(String username);

	public User updateUser(User user);
}
