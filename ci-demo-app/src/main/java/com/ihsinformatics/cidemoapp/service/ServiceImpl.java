/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/
package com.ihsinformatics.cidemoapp.service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.repository.EmployeeRepository;
import com.ihsinformatics.cidemoapp.repository.EventRepository;
import com.ihsinformatics.cidemoapp.repository.GroupRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Component
public class ServiceImpl implements Service {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getGroup(java.lang.Long)
	 */
	@Override
	public Group getGroup(Long id) {
		return groupRepository.findById(BigInteger.valueOf(id)).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getGroups()
	 */
	@Override
	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getGroups(java.lang.String)
	 */
	@Override
	public Group getGroupByName(String name) {
		return groupRepository.findByName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#saveGroup(com.ihsinformatics.
	 * cidemoapp.model.Group)
	 */
	@Override
	public Group saveGroup(Group group) {
		return groupRepository.save(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#updateGroup(com.ihsinformatics.
	 * cidemoapp.model.Group)
	 */
	@Override
	public Group updateGroup(Group group) {
		return groupRepository.save(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#deleteGroup(com.ihsinformatics.
	 * cidemoapp.model.Group)
	 */
	@Override
	public void deleteGroup(Group group) {
		groupRepository.delete(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getEmployee(java.lang.Long)
	 */
	@Override
	public Employee getEmployee(Long id) {
		return employeeRepository.findById(BigInteger.valueOf(id)).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getEmployees()
	 */
	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#getEmployees(java.lang.String)
	 */
	@Override
	public List<Employee> getEmployees(String name) {
		return employeeRepository.findByName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#saveEmployee(com.ihsinformatics.
	 * cidemoapp.model.Employee)
	 */
	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#updateEmployee(com.
	 * ihsinformatics.cidemoapp.model.Employee)
	 */
	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#deleteEmployee(com.
	 * ihsinformatics.cidemoapp.model.Employee)
	 */
	@Override
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getEvent(java.lang.Long)
	 */
	@Override
	public Event getEvent(Long id) {
		return eventRepository.findById(BigInteger.valueOf(id)).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getEvents()
	 */
	@Override
	public List<Event> getEvents() {
		return eventRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.cidemoapp.service.Service#getEvents(java.lang.String)
	 */
	@Override
	public List<Event> getEvents(String title) {
		return eventRepository.findByTitle(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#getEvents(java.time.Instant,
	 * java.time.Instant)
	 */
	@Override
	public List<Event> getEvents(Instant from, Instant to) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").gte(from)).addCriteria(Criteria.where("date").lte(to));
		return mongoTemplate.find(query, Event.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#saveEvent(com.ihsinformatics.
	 * cidemoapp.model.Event)
	 */
	@Override
	public Event saveEvent(Event event) {
		return eventRepository.save(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#updateEvent(com.ihsinformatics.
	 * cidemoapp.model.Event)
	 */
	@Override
	public Event updateEvent(Event event) {
		return eventRepository.save(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.cidemoapp.service.Service#deleteEvent(com.ihsinformatics.
	 * cidemoapp.model.Event)
	 */
	@Override
	public void deleteEvent(Event event) {
		eventRepository.delete(event);
	}
}
