package com.dronesfree.security.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dronesfree.security.dto.ExampleUserDetails;
import com.dronesfree.user.model.User;
import com.dronesfree.user.service.IUserService;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	private static final Logger LOGGER = Logger
			.getLogger(RepositoryUserDetailsService.class);

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		LOGGER.debug("Loading user by username: {" + username + "}");

		User user = userService.getUserByUsername(username);
		LOGGER.debug("Found user: {" + user + "}");

		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: "
					+ username);
		}

		ExampleUserDetails principal = ExampleUserDetails.getBuilder()
				.firstName(user.getFirstName()).id(user.getId())
				.lastName(user.getLastName()).password(user.getPassword())
				.role(user.getRole())
				.socialSignInProvider(user.getSignInProvider())
				.username(user.getUsername()).build();

		LOGGER.debug("Returning user details: {" + principal + "}");

		return principal;

	}

}
