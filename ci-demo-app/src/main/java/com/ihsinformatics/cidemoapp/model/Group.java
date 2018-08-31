/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
	@Id
	@Column(name = "uuid", updatable = false, nullable = false)
	private String uuid = UUID.randomUUID().toString();
	@NonNull
	private String name;
	private String address;
	private String city;
	private String stateOrProvince;
	private String country;
	private String postalCode;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Event> events;

	public Group() {
		this(null);
	}

	public Group(String name) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
	}
}
