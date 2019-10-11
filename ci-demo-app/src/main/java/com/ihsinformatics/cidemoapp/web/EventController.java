/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
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

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.service.Service;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RestController
@RequestMapping("/api")
public class EventController {

	private final Logger log = LoggerFactory.getLogger(EventController.class);
	private Service service;

	public EventController(Service service) {
		this.service = service;
	}

	@GetMapping("/events")
	public Collection<Event> events() {
		return service.getEvents();
	}

	@GetMapping("/event/{uuid}")
	public ResponseEntity<Event> getEvent(@PathVariable String uuid) {
		Optional<Event> group = Optional.of(service.getEvent(uuid));
		return group.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/event")
	public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) throws URISyntaxException {
		log.info("Request to create event: {}", event);
		Event result = service.saveEvent(event);
		event.getAttendees().forEach(attendee -> saveEmployee(attendee));
		return ResponseEntity.created(new URI("/api/event/" + result.getUuid())).body(result);
	}

	private Employee saveEmployee(Employee attendee) {
		List<Employee> list = service.getEmployeesByName(attendee.getName());
		if (list.isEmpty()) {
			service.saveEmployee(new Employee(null, attendee.getName()));
		}
		return attendee;
	}

	@PutMapping("/event/{uuid}")
	public ResponseEntity<Event> updateEvent(@PathVariable String uuid, @Valid @RequestBody Event event) {
		event.setUuid(uuid);
		log.info("Request to update event: {}", event);
		Event result = service.saveEvent(event);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/event/{uuid}")
	public ResponseEntity<?> deleteEvent(@PathVariable String uuid) {
		log.info("Request to delete event: {}", uuid);
		service.deleteEvent(service.getEvent(uuid));
		return ResponseEntity.ok().build();
	}
}
