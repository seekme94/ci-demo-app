/**
 * 
 */
package com.ihsinformatics.cidemoapp.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

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
import com.ihsinformatics.cidemoapp.model.Group;
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
		Group group = mock(Group.class);
		assertNotNull(group);
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroup(java.lang.String)}.
	 */
	@Test
	public void shouldGetGroupByUuid() {
		Group dev = Group.builder().name("Developers").build();
		when(groupRepository.findByUuid(any(String.class))).thenReturn(dev);
		Group group = service.getGroup(dev.getName());
		assertThat(dev, Matchers.is(group));
		verify(groupRepository, times(1)).findByUuid(any(String.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroups()}.
	 */
	@Test
	public void shouldGetGroups() {
		Group dev = Group.builder().name("Developers").build();
		Group qa = Group.builder().name("QA").build();
		Group admin = Group.builder().name("Administration").build();
		when(groupRepository.findAll()).thenReturn(Arrays.asList(dev, qa, admin));
		List<Group> list = service.getGroups();
		assertThat(list, Matchers.hasItems(dev, qa, admin));
		verify(groupRepository, times(1)).findAll();
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getGroupByName(java.lang.String)}.
	 */
	@Test
	public void shouldGetGroupByName() {
		Group dev = Group.builder().name("Developers").build();
		when(groupRepository.findByName(any(String.class))).thenReturn(dev);
		Group group = service.getGroupByName(dev.getUuid());
		assertThat(dev, Matchers.is(group));
		verify(groupRepository, times(1)).findByName(any(String.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void shouldSaveGroup() {
		Group dev = Group.builder().name("Developers").address("503, Ibrahim trade towers").city("Karachi")
				.stateOrProvince("Sindh").country("Pakistan").build();
		when(groupRepository.save(any(Group.class))).thenReturn(dev);
		dev = service.saveGroup(dev);
		assertNotNull(dev.getUuid());
		verify(groupRepository, times(1)).save(any(Group.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void shouldUpdateGroup() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public void shouldDeleteGroup() {
		Group dev = Group.builder().name("Developers").build();
		doNothing().when(groupRepository).delete(any(Group.class));
		service.deleteGroup(dev);
		verify(groupRepository, times(1)).delete(any(Group.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployee(java.lang.String)}.
	 */
	@Test
	public void shouldGetEmployee() {
		Employee owais = Employee.builder().name("Owais").build();
		when(employeeRepository.findByUuid(any(String.class))).thenReturn(owais);
		Employee employee = service.getEmployee(owais.getUuid());
		assertThat(owais, Matchers.is(employee));
		verify(employeeRepository, times(1)).findByUuid(any(String.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployees()}.
	 */
	@Test
	public void shouldGetEmployees() {
		Employee owais = Employee.builder().name("Owais").build();
		Employee rabbia = Employee.builder().name("Rabbia").build();
		Employee tahira = Employee.builder().name("Tahira").build();
		List<Employee> list = Arrays.asList(owais, rabbia, tahira);
		when(employeeRepository.findAll()).thenReturn(list);
		assertThat(service.getEmployees(), Matchers.hasItems(owais, rabbia, tahira));
		verify(employeeRepository, times(1)).findAll();
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployeesByName(java.lang.String)}.
	 */
	@Test
	public void shouldGetEmployeesByName() {
		Employee owais = Employee.builder().name("Owais").build();
		List<Employee> list = Arrays.asList(owais);
		when(employeeRepository.findByName(any(String.class))).thenReturn(list);
		assertThat(service.getEmployeesByName(owais.getName()), Matchers.hasItems(owais));
		verify(employeeRepository, times(1)).findByName(any(String.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEmployeesByName(java.lang.String)}.
	 */
	@Test
	public void shouldThrowExceptionOnGetAdminEmployee() {
		when(employeeRepository.findByName(anyString())).thenThrow(SecurityException.class);
		List<Employee> admin = service.getEmployeesByName("admin");
		assertNull(admin);
		verifyZeroInteractions(employeeRepository);
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void shouldSaveEmployee() {
		Employee owais = Employee.builder().name("Owais").build();
		when(employeeRepository.save(any(Employee.class))).thenReturn(owais);
		owais = service.saveEmployee(owais);
		assertNotNull(owais.getUuid());
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void shouldUpdateEmployee() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	public void shouldDeleteEmployee() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvent(java.lang.String)}.
	 */
	@Test
	public void shouldGetEvent() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvents()}.
	 */
	@Test
	public void shouldGetEvents() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEventsByTitle(java.lang.String)}.
	 */
	@Test
	public void shouldGetEventsByTitle() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#getEvents(java.time.Instant, java.time.Instant)}.
	 */
	@Test
	public void shouldGetEventsInstantInstant() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#saveEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void shouldSaveEvent() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#updateEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void shouldUpdateEvent() {
		// TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServiceImpl#deleteEvent(com.ihsinformatics.cidemoapp.model.Event)}.
	 */
	@Test
	public void shouldDeleteEvent() {
		// TODO
	}
}
