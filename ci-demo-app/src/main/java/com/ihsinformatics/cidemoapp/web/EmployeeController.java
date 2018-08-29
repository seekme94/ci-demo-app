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

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.service.Service;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

	private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	private Service service;

	public EmployeeController(Service service) {
		this.service = service;
	}

	@GetMapping("/employees")
	Collection<Employee> employees() {
		return service.getEmployees();
	}

	@GetMapping("/employee/{id}")
	ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		Optional<Employee> employee = Optional.of(service.getEmployee(id));
		return employee.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<Employee>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/employee")
	ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
		log.info("Request to create employee: {}", employee);
		Employee result = service.saveEmployee(employee);
		return ResponseEntity.created(new URI("/api/employee/" + result.getId())).body(result);
	}

	@PutMapping("/employee/{id}")
	ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
		employee.setId(BigInteger.valueOf(id));
		log.info("Request to update employee: {}", employee);
		Employee result = service.saveEmployee(employee);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		log.info("Request to delete employee: {}", id);
		service.deleteEmployee(service.getEmployee(id));
		return ResponseEntity.ok().build();
	}
}
