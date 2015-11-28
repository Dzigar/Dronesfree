package com.dronesfree.user.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dronesfree.order.service.IOrderService;
import com.dronesfree.user.model.User;
import com.dronesfree.user.service.IUserService;

/**
 * 
 * @author Dzigar
 * 
 */
@Controller
@RequestMapping(value = "/{username}")
public class UserProfileController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IOrderService orderService;

	private static final String USER_PAGE = "userPage";
	public static final String PAGE_NOTFOUND = "403";

	private static final Logger log = LoggerFactory
			.getLogger(UserProfileController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showUserPage(@PathVariable("username") String username,
			ModelMap modelMap, Principal principal) {
		User user = userService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			return USER_PAGE;
		}
		log.debug("User with username " + username + " does not exsit!");
		return PAGE_NOTFOUND;
	}

	@RequestMapping(value = "/removeUserAccount", method = RequestMethod.POST)
	public String removeUserAccount(Principal principal) {
		// if the profile was deleted then the user will be redirected to
		// welcome page else, return user page
		userService.removeUserProfile(principal.getName());
		return "redirect:/logout";

	}

}
