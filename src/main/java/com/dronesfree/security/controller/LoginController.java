package com.dronesfree.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.dronesfree.user.dto.RegistrationForm;
import com.dronesfree.user.model.SocialMediaService;

@Controller
public class LoginController {

	private static final Logger LOGGER = Logger
			.getLogger(LoginController.class);

	private final String VIEW_NAME_LOGIN_PAGE = "welcomePage";
	private final String ACCESSDENIED_PAGE = "403";
	private static final String MODEL_NAME_REGISTRATION_DTO = "userForm";

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(WebRequest request, Model model) {

		LOGGER.debug("Rendering login page.");
		Connection<?> connection = ProviderSignInUtils.getConnection(request);

		RegistrationForm registration = createRegistrationDTO(connection);

		model.addAttribute(MODEL_NAME_REGISTRATION_DTO, registration);
		return VIEW_NAME_LOGIN_PAGE;
	}

	@RequestMapping(value = "/error-login")
	public String errorLogin() {
		return "redirect:/login";
	}

	// for 403 access denied mvn
	@RequestMapping(value = { "/403" }, method = RequestMethod.GET)
	public String error(HttpServletRequest request) {
		LOGGER.info("Error request for URL: " + request.getRequestURI());
		return ACCESSDENIED_PAGE;
	}

	/**
	 * Creates the form object used in the registration form.
	 * 
	 * @param connection
	 * @return If a user is signing in by using a social provider, this method
	 *         returns a form object populated by the values given by the
	 *         provider. Otherwise this method returns an empty form object
	 *         (normal form registration).
	 */
	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
		RegistrationForm dto = new RegistrationForm();

		if (connection != null) {
			UserProfile socialMediaProfile = connection.fetchUserProfile();
			dto.setEmail(socialMediaProfile.getEmail());
			dto.setFirstName(socialMediaProfile.getFirstName());
			dto.setLastName(socialMediaProfile.getLastName());

			ConnectionKey providerKey = connection.getKey();
			dto.setSignInProvider(SocialMediaService.valueOf(providerKey
					.getProviderId().toUpperCase()));
		}

		return dto;
	}
}
