/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/
package com.ihsinformatics.cidemoapp.service;

import java.time.Instant;
import java.util.List;

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public interface Service {

	Group getGroup(Long id);

	List<Group> getGroups();

	Group getGroupByName(String name);

	Group saveGroup(Group group);

	Group updateGroup(Group group);

	void deleteGroup(Group group);

	Employee getEmployee(Long id);

	List<Employee> getEmployees();

	List<Employee> getEmployees(String name);

	Employee saveEmployee(Employee employee);

	Employee updateEmployee(Employee employee);

	void deleteEmployee(Employee employee);

	Event getEvent(Long id);

	List<Event> getEvents();

	List<Event> getEvents(String title);

	List<Event> getEvents(Instant from, Instant to);

	Event saveEvent(Event event);

	Event updateEvent(Event event);

	void deleteEvent(Event event);
}
