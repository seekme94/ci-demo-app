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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.service.Service;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	private final Service service;

	public Initializer(Service service) {
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		createGroups();
		createEmployees();
		createEvents();
		service.getEvents().forEach(System.out::println);
	}

	private void createGroups() {
		Stream.of("Developer", "Quality Assurance", "Administration", "Human Resource")
				.forEach(name -> saveGroup(new Group(name)));
	}

	private Group saveGroup(Group group) {
		Group exist = service.getGroupByName(group.getName());
		if (exist == null) {
			group = service.saveGroup(group);
		}
		return group;
	}

	private void createEmployees() {
		Stream.of("Owais", "Naveed", "Tahira", "Omar", "Adnan", "Moiz", "Babar", "Irtiza", "Ahsan", "Shahid")
				.forEach(name -> saveEmployee(new Employee(name)));
	}

	private Employee saveEmployee(Employee employee) {
		List<Employee> exist = service.getEmployeesByName(employee.getName());
		if (exist.isEmpty()) {
			employee = service.saveEmployee(employee);
		}
		return employee;
	}

	private void createEvents() {
		Group developer = service.getGroupByName("Developer");
		Set<Employee> attendees = new HashSet<Employee>();
		for (Employee employee : Arrays.asList(new Employee("Owais"), new Employee("Naveed"), new Employee("Tahira"))) {
			attendees.add(employee);
		}
		Event devEvent = Event.builder().title("Full Stack Reactive App")
				.description("Reactive with Spring Boot + React").date(Instant.parse("2018-08-10T12:00:00.000Z"))
				.attendees(attendees).build();
		developer.setEvents(Collections.singleton(devEvent));
		service.saveEvent(devEvent);

		Group qa = service.getGroupByName("Quality Assurance");
		attendees = new HashSet<Employee>();
		attendees.addAll(Arrays.asList(new Employee("Omar"), new Employee("Adnan")));
		Event qaEvent = Event.builder().title("Mockito").description("Introduction to Mockito library for unit testing")
				.date(Instant.parse("2018-05-15T09:30:00.000Z")).attendees(attendees).build();
		qa.setEvents(Collections.singleton(qaEvent));
		service.saveEvent(qaEvent);

		Group admin = service.getGroupByName("Administration");
		attendees = new HashSet<Employee>();
		attendees.addAll(Arrays.asList(new Employee("Omar"), new Employee("Naveed"), new Employee("Babar"),
				new Employee("Moiz"), new Employee("Irtiza"), new Employee("Ahsan"), new Employee("Shahid")));
		Event adminEvent = Event.builder().title("Cricket Tournament 2018")
				.date(Instant.parse("2018-07-28T10:00:00.000Z")).attendees(attendees).build();
		admin.setEvents(Collections.singleton(adminEvent));
		service.saveEvent(adminEvent);
	}
}
