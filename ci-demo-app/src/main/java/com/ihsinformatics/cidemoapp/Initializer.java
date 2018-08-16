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
import java.util.Collections;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.model.MongoGroupRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	private final MongoGroupRepository repository;

	public Initializer(MongoGroupRepository repository) {
		this.repository = repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		createGroups();
		createEvents();
		repository.findAll().forEach(System.out::println);
	}

	private void createGroups() {
		Stream.of("Developer", "Quality Assurance", "Infrastructure", "Human Resource")
				.forEach(name -> saveOrUpdateGroup(new Group(name)));
	}

	private Group saveOrUpdateGroup(Group group) {
		Group exist = repository.findByName(group.getName());
		if (exist != null) {
			group.setId(exist.getId());
		}
		return repository.save(group);
	}

	private void createEvents() {
		Group developer = repository.findByName("Developer");
		Event devEvent = Event.builder().title("Full Stack Reactive App")
				.description("Reactive with Spring Boot + React").date(Instant.parse("2018-08-10T12:00:00.000Z"))
				.build();
		developer.setEvents(Collections.singleton(devEvent));
		repository.save(developer);

		Group qa = repository.findByName("Quality Assurance");
		Event qaEvent = Event.builder().title("Mockito").description("Introduction to Mockito library for unit testing")
				.date(Instant.parse("2018-05-15T09:30:00.000Z")).build();
		qa.setEvents(Collections.singleton(qaEvent));
		repository.save(qa);
	}
}
