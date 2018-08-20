/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.model.GroupRepository;
import com.ihsinformatics.cidemoapp.model.User;
import com.ihsinformatics.cidemoapp.model.UserRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	private final GroupRepository groupRepository;

	@Autowired
	private final UserRepository userRepository;

	public Initializer(GroupRepository groupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		createUsers();
		createGroups();
		createEvents();
		userRepository.findAll().forEach(System.out::println);
		groupRepository.findAll().forEach(System.out::println);
	}

	private void createUsers() {
		List<User> list = Arrays.asList(new User(null, "ihs", "info@ihsinformatics.com"),
				new User(null, "owais", "owais.hussain@ihsinformatics.com"));
		list.forEach(user -> saveOrUpdateUser(user));
	}

	private void createGroups() {
		Stream.of("Developer", "Quality Assurance", "Infrastructure", "Human Resource")
				.forEach(name -> saveOrUpdateGroup(new Group(name)));
	}

	private Object saveOrUpdateUser(User user) {
		List<User> exist = userRepository.findAllByName(user.getName());
		if (!exist.isEmpty()) {
			user.setId(exist.get(0).getId());
		}
		return userRepository.save(user);
	}

	private Group saveOrUpdateGroup(Group group) {
		Group exist = groupRepository.findByName(group.getName());
		if (exist != null) {
			group.setId(exist.getId());
		}
		return groupRepository.save(group);
	}

	private void createEvents() {
		Group developer = groupRepository.findByName("Developer");
		Event devEvent = Event.builder().title("Full Stack Reactive App")
				.description("Reactive with Spring Boot + React").date(Instant.parse("2018-08-10T12:00:00.000Z"))
				.build();
		developer.setEvents(Collections.singleton(devEvent));
		groupRepository.save(developer);

		Group qa = groupRepository.findByName("Quality Assurance");
		Event qaEvent = Event.builder().title("Mockito").description("Introduction to Mockito library for unit testing")
				.date(Instant.parse("2018-05-15T09:30:00.000Z")).build();
		qa.setEvents(Collections.singleton(qaEvent));
		groupRepository.save(qa);
	}
}
