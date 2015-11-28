package com.dronesfree.security.util;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dronesfree.security.dto.ExampleUserDetails;
import com.dronesfree.user.model.User;

public class SecurityUtil {

	private static final Logger LOGGER = Logger.getLogger(SecurityUtil.class);

	public static void logInUser(User user) {
		LOGGER.info("Logging in user: " + user);

		ExampleUserDetails userDetails = ExampleUserDetails.getBuilder()
				.firstName(user.getFirstName()).id(user.getId())
				.lastName(user.getLastName()).password(user.getPassword())
				.role(user.getRole())
				.socialSignInProvider(user.getSignInProvider())
				.username(user.getUsername()).build();
		LOGGER.debug("Logging in principal: " + userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		LOGGER.info("User:" + userDetails + " has been logged in.");
	}
}