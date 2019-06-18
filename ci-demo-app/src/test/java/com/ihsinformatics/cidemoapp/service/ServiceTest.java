/**
 * 
 */
package com.ihsinformatics.cidemoapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.repository.EmployeeRepository;
import com.ihsinformatics.cidemoapp.repository.EventRepository;
import com.ihsinformatics.cidemoapp.repository.GroupRepository;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class ServiceTest {

	@Mock
	private GroupRepository groupRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private EventRepository eventRepository;

	@Mock
	MongoTemplate mongoTemplate;
	
	@InjectMocks
	private ServiceImpl service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNull() {
		Employee employee = mock(Employee.class);
		assertNotNull(employee);
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroup(java.lang.String)}.
	 */
	@Test
	public void testGetGroup() {
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroups()}.
	 */
	@Test
	public void testGetGroups() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroupByName(java.lang.String)}.
	 */
	@Test
	public void testGetGroupByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void testSaveGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void testUpdateGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void testDeleteGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployee(java.lang.String)}.
	 */
	@Test
	public void testGetEmployee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployees()}.
	 */
	@Test
	public void testGetEmployees() {
		Employee owais = new Employee("Owais");
		Employee rabbia = new Employee("Rabbia");
		Employee tahira = new Employee("Tahira");
		List<Employee> list = Arrays.asList(owais, rabbia, tahira);
		when(service.getEmployees()).thenReturn(list);
		assertThat(list, Matchers.hasItems(owais, rabbia, tahira));
		verify(employeeRepository, times(1)).findAll();
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployeesByName(java.lang.String)}.
	 */
	@Test
	public void testGetEmployeesByName() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployeesByName(java.lang.String)}.
	 */
	@Test
	public void shouldThrowExceptionOnGetAdminEmployee() {
		when(employeeRepository.findByName(anyString())).thenThrow(SecurityException.class);
		List<Employee> admin = service.getEmployeesByName("admin");
		assertNull(admin);
		verifyZeroInteractions(employeeRepository);
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void testSaveEmployee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void testDeleteEmployee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvent(java.lang.String)}.
	 */
	@Test
	public void testGetEvent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvents()}.
	 */
	@Test
	public void testGetEvents() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEventsByTitle(java.lang.String)}.
	 */
	@Test
	public void testGetEventsByTitle() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvents(java.time.Instant, java.time.Instant)}.
	 */
	@Test
	public void testGetEventsInstantInstant() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void testSaveEvent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void testUpdateEvent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void testDeleteEvent() {
		fail("Not yet implemented");
	}

}
