/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp.web;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihsinformatics.cidemoapp.model.User;
import com.ihsinformatics.cidemoapp.model.UserRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(GroupController.class);
	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	Collection<User> groups() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{name}")
	Collection<User> getUserByName(@PathVariable String name) {
		return userRepository.findAllByName(name);
	}

	@GetMapping("/user/{id}")
	ResponseEntity<User> getUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(BigInteger.valueOf(id));
		return user.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/user")
	ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
		log.info("Request to create user: {}", user);
		User result = userRepository.save(user);
		return ResponseEntity.created(new URI("/api/user/" + result.getId())).body(result);
	}

	@PutMapping("/user/{id}")
	ResponseEntity<User> updateGroup(@PathVariable Long id, @Valid @RequestBody User user) {
		user.setId(BigInteger.valueOf(id));
		log.info("Request to update user: {}", user);
		User result = userRepository.save(user);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		log.info("Request to delete user: {}", id);
		userRepository.deleteById(BigInteger.valueOf(id));
		return ResponseEntity.ok().build();
	}
}
