/* Copyright(C) 2018 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
*/

package com.ihsinformatics.cidemoapp.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsinformatics.cidemoapp.model.Group;
import com.ihsinformatics.cidemoapp.service.ServiceImpl;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

	protected static String API_PREFIX = "/api/";

	protected MockMvc mockMvc;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Mock
	private ServiceImpl service;

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
	public void shouldGetGroup() throws Exception {
		Group group = Group.builder().name("Test Group").build();
		String uuid = UUID.randomUUID().toString();
		group.setUuid(uuid);
		when(service.getGroup(uuid.toString())).thenReturn(group);
		ResultActions actions = mockMvc.perform(get(API_PREFIX + "group/{uuid}", group.getUuid()));
		actions.andExpect(status().isOk());
		actions.andExpect(jsonPath("$.name", Matchers.is(group.getName())));
		verify(service, times(1)).getGroup(uuid.toString());
	}

	@Test
	public void shouldGetGroups() throws Exception {
		List<Group> groups = Arrays.asList(Group.builder().name("Test Group 1").build(),
				Group.builder().name("Test Group 2").build(), Group.builder().name("Test Group 3").build());
		when(service.getGroups()).thenReturn(groups);
		ResultActions actions = mockMvc.perform(get(API_PREFIX + "groups"));
		actions.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		actions.andExpect(jsonPath("$", Matchers.hasSize(3)));
		verify(service, times(1)).getGroups();
		verifyNoMoreInteractions(service);
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.web.GroupController#createGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldCreateGroup() throws Exception {
		Group dev = Group.builder().name("Developers").build();
		when(service.saveGroup(any(Group.class))).thenReturn(dev);
		String content = gson.toJson(dev);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_PREFIX + "group")
				.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8).content(content);
		ResultActions actions = mockMvc.perform(requestBuilder);
		actions.andExpect(status().isCreated());
		String expectedUrl = API_PREFIX + "group/" + dev.getUuid();
		actions.andExpect(MockMvcResultMatchers.redirectedUrl(expectedUrl));
		verify(service, times(1)).saveGroup(any(Group.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.web.GroupController#updateGroup(java.lang.Long, com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void shouldUpdateGroup() {
		// TODO:
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.web.GroupController#deleteGroup(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldDeleteGroup() throws Exception {
		Group dev = Group.builder().name("Developers").build();
		when(service.getGroup(any(String.class))).thenReturn(dev);
		doNothing().when(service).deleteGroup(dev);
		ResultActions actions = mockMvc.perform(delete(API_PREFIX + "group/{uuid}", dev.getUuid()));
		actions.andExpect(status().isNoContent());
		verify(service, times(1)).getGroup(dev.getUuid());
		verify(service, times(1)).deleteGroup(dev);
		verifyNoMoreInteractions(service);
	}
}
