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

import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.model.MongoGroupRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RestController
@RequestMapping("/api")
public class GroupController {

	private final Logger log = LoggerFactory.getLogger(GroupController.class);
	private MongoGroupRepository groupRepository;

	public GroupController(MongoGroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@GetMapping("/groups")
	Collection<Group> groups() {
		List<Group> list = groupRepository.findAll();
		return list;
	}

	@GetMapping("/group/{id}")
	ResponseEntity<Group> getGroup(@PathVariable Long id) {
		Optional<Group> group = groupRepository.findById(BigInteger.valueOf(id));
		return group.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<Group>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/group")
	ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
		log.info("Request to create group: {}", group);
		Group result = groupRepository.save(group);
		return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
	}

	@PutMapping("/group/{id}")
	ResponseEntity<Group> updateGroup(@PathVariable Long id, @Valid @RequestBody Group group) {
		group.setId(BigInteger.valueOf(id));
		log.info("Request to update group: {}", group);
		Group result = groupRepository.save(group);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/group/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		log.info("Request to delete group: {}", id);
		groupRepository.deleteById(BigInteger.valueOf(id));
		return ResponseEntity.ok().build();
	}
}
