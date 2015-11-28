package com.dronesfree.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dronesfree.user.dto.RegistrationForm;
import com.dronesfree.user.model.User;
import com.dronesfree.user.service.DuplicateEmailException;
import com.dronesfree.user.service.IUserService;

@RestController
public class UserRestController {

	@Autowired
	private IUserService userService;

	private static final Logger log = Logger
			.getLogger(UserRestController.class);

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		log.info("Fetching User with id " + id);
		User user = userService.getUserById(id);
		if (user == null) {
			log.error("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> createNewUserAccount(
			@RequestBody RegistrationForm userForm)
			throws DuplicateEmailException {
		User user = userService.registerNewUserAccount(userForm);
		if (user == null) {
			new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
}
