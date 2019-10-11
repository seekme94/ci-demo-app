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

import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.service.Service;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RestController
@RequestMapping("/api")
public class GroupController {

	private final Logger log = LoggerFactory.getLogger(GroupController.class);
	private Service service;

	public GroupController(Service service) {
		this.service = service;
	}

	@GetMapping("/groups")
	public Collection<Group> groups() {
		return service.getGroups();
	}

	@GetMapping("/group/{uuid}")
	public ResponseEntity<Group> getGroup(@PathVariable String uuid) {
		Optional<Group> group = Optional.of(service.getGroup(uuid));
		return group.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<Group>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/group")
	public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
		log.info("Request to create group: {}", group);
		Group result = service.saveGroup(group);
		return ResponseEntity.created(new URI("/api/group/" + result.getUuid())).body(result);
	}

	@PutMapping("/group/{uuid}")
	public ResponseEntity<Group> updateGroup(@PathVariable String uuid, @Valid @RequestBody Group group) {
		group.setUuid(uuid);
		log.info("Request to update group: {}", group);
		Group result = service.saveGroup(group);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/group/{uuid}")
	public ResponseEntity<?> deleteGroup(@PathVariable String uuid) {
		log.info("Request to delete group: {}", uuid);
		service.deleteGroup(service.getGroup(uuid));
		return ResponseEntity.noContent().build();
	}
}
