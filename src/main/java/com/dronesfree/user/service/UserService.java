package com.dronesfree.user.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.user.dao.UserDao;
import com.dronesfree.user.dto.RegistrationForm;
import com.dronesfree.user.model.User;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	@Override
	public User registerNewUserAccount(RegistrationForm userAccountData)
			throws DuplicateEmailException {
		LOGGER.debug("Registering new user account with information:"
				+ userAccountData);
		if (emailExist(userAccountData.getEmail())) {
			LOGGER.debug("Email: " + userAccountData.getEmail() + "exists.");
			throw new DuplicateEmailException("The email address: "
					+ userAccountData.getEmail() + " is already in use.");
		}
		LOGGER.debug("Email:" + userAccountData.getEmail()
				+ " does not exist. Continuing registration.");

		User.Builder user = User.getBuilder().email(userAccountData.getEmail())
				.username(userAccountData.getUsername())
				.firstName(userAccountData.getFirstName())
				.lastName(userAccountData.getLastName())
				.password(encodePassword(userAccountData));

		if (userAccountData.isSocialSignIn()) {
			user.signInProvider(userAccountData.getSignInProvider());
		}

		User registered = user.build();

		LOGGER.debug("Persisting new user with information: " + registered);
		return userDao.save(registered);
	}

	@Override
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void removeUserProfile(String username) {
		userDao.removeUser(getUserByUsername(username));
	}

	@Override
	public User getUserById(long id) {
		return userDao.getUserById(id);
	}

	private boolean emailExist(String username) {
		LOGGER.debug("Checking if email '" + username
				+ "' is already found from the database.");

		User user = userDao.getUserByUsername(username);

		if (user != null) {
			LOGGER.debug("User account: " + user + " found with email: "
					+ username + ". Returning true.");
			return true;
		}

		LOGGER.debug("No user account found with email: " + username
				+ ". Returning false.");

		return false;
	}

	private String encodePassword(RegistrationForm dto) {
		String encodedPassword = null;

		if (dto.isNormalRegistration()) {
			LOGGER.debug("Registration is normal registration. Encoding password.");
			encodedPassword = passwordEncoder.encode(dto.getPassword());
		}

		return encodedPassword;
	}

	@Override
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

}
