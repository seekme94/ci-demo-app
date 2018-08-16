/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.model.MongoGroupRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

	protected static final String API_PREFIX = "/api/";

	protected MockMvc mockMvc;

	@Mock
	private MongoGroupRepository repository;

	@InjectMocks
	protected GroupController groupController;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.web.GroupController#getGroup(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testGetGroupById() throws Exception {
		Group group = new Group("Test Group");
		BigInteger id = new BigInteger("1");
		group.setId(id);
		when(repository.findGroupById(id)).thenReturn(group);
		ResultActions actions = mockMvc.perform(get(API_PREFIX + "group/{id}", group.getId().longValue()));
		actions.andExpect(status().isOk());
		actions.andExpect(jsonPath("$.name", Matchers.is(group.getName())));
		verify(repository, times(1)).findById(id);
	}

	@Test
	public final void testGetAllGroups() throws Exception {
		List<Group> groups = Arrays.asList(new Group("Test Group 1"), new Group("Test Group 2"), new Group("Test Group 3"));
		when(repository.findAll()).thenReturn(groups);
		ResultActions actions = mockMvc.perform(get(API_PREFIX + "groups"));
		actions.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		actions.andExpect(jsonPath("$", Matchers.hasSize(3)));
		verify(repository, times(1)).findAll();
		verifyNoMoreInteractions(repository);
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.web.GroupController#createGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public final void testCreateGroup() {
		// TODO:
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.web.GroupController#updateGroup(java.lang.Long, com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public final void testUpdateGroup() {
		// TODO:
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.web.GroupController#deleteGroup(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteGroup() {
		// TODO:
	}
}
