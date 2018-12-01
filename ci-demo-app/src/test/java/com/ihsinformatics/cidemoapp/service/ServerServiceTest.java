/**
 * 
 */
package com.ihsinformatics.cidemoapp.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.ihsinformatics.cidemoapp.model.Group;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * @author owais
 *
 */
public class ServerServiceTest {

	static ServerService service;
	static MongoClient mongoClient;
	static DB database;
	static DBCollection groups;
	static DBCollection employees;
	static DBCollection events;

	static String employeeUuid = "aaaaaaaa-1111-2222-3333-eeeeeeeeeeee";
	static String groupUuid = "aaaaaaaa-2222-3333-4444-eeeeeeeeeeee";

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDB("testdb");
		employees = database.getCollection("employee");
		events = database.getCollection("event");
		groups = database.getCollection("group");
		service = new ServerService("testdb");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		database.dropDatabase();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create all data required for testing
		BasicDBObject testEmployee = new BasicDBObject();
		testEmployee.put("uuid", employeeUuid);
		testEmployee.put("name", "Test Employee");
		employees.insert(testEmployee);

		testEmployee = new BasicDBObject();
		testEmployee.put("uuid", UUID.randomUUID().toString());
		testEmployee.put("name", "Another Test Employee");
		employees.insert(testEmployee);

		BasicDBObject testgroup = new BasicDBObject();
		testgroup.put("uuid", groupUuid);
		testgroup.put("name", "Test Group");
		groups.insert(testgroup);

		testgroup = new BasicDBObject();
		testgroup.put("uuid", UUID.randomUUID().toString());
		testgroup.put("name", "Another Test Group");
		groups.insert(testgroup);

		testgroup = new BasicDBObject();
		testgroup.put("uuid", UUID.randomUUID().toString());
		testgroup.put("name", "Complete Test Group");
		testgroup.put("address", "408, Ibrahim Trade Tower");
		testgroup.put("city", "Karachi");
		testgroup.put("country", "Pakistan");
		groups.insert(testgroup);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		employees.drop();
		groups.drop();
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#objectToString(java.lang.Object)}.
	 */
	@Test
	public final void testObjectToString() {
		String obj = "some_string";
		assertEquals(obj, service.objectToString(obj));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#objectToString(java.lang.Object)}.
	 */
	@Test
	public final void testNullObjectToString() {
		assertEquals(null, service.objectToString(null));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getGroup(java.lang.String)}.
	 */
	@Test
	public final void testGetGroup() {
		Group group = service.getGroup(groupUuid);
		assertNotNull(group);
		assertTrue(group.getName().equals("Test Group"));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getGroups()}.
	 */
	@Test
	public final void testGetGroups() {
		List<Group> list = service.getGroups();
		assertEquals(list.size(), groups.count());
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getGroupByName(java.lang.String)}.
	 */
	@Test
	public final void testGetGroupByName() {
		Group group = service.getGroupByName("Test Group");
		assertNotNull(group);
		assertTrue(group.getName().equals("Test Group"));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#saveGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public final void testSaveGroup() {
		long before = groups.count();
		service.saveGroup(new Group("Test Save"));
		long after = groups.count();
		assertTrue(after == before + 1);
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#updateGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public final void testUpdateGroup() {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "Test Group");
		DBCursor cursor = groups.find(searchQuery);
		Group testGroup = new Group();
		if (cursor.hasNext()) {
			DBObject object = cursor.next();
			testGroup.setUuid(object.get("uuid").toString());
			testGroup.setName(object.get("name").toString());
		}
		testGroup.setName("Test Group Update");
		service.updateGroup(testGroup);
		testGroup = service.getGroup(groupUuid);
		assertTrue(testGroup.getName().equals("Test Group Update"));
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#deleteGroup(com.ihsinformatics.cidemoapp.model.Group)}.
	 */
	@Test
	public final void testDeleteGroup() {
		long before = groups.count();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("uuid", groupUuid);
		DBCursor cursor = groups.find(searchQuery);
		Group testGroup = new Group();
		if (cursor.hasNext()) {
			DBObject object = cursor.next();
			testGroup.setUuid(object.get("uuid").toString());
			testGroup.setName(object.get("name").toString());
		}
		service.deleteGroup(testGroup);
		long after = groups.count();
		assertTrue(after == before - 1);
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getEmployee(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testGetEmployee() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getEmployees()}.
	 */
	@Test
	@Ignore
	public final void testGetEmployees() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#getEmployeesByName(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testGetEmployeesByName() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#saveEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	@Ignore
	public final void testSaveEmployee() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#updateEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	@Ignore
	public final void testUpdateEmployee() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.ihsinformatics.cidemoapp.service.ServerService#deleteEmployee(com.ihsinformatics.cidemoapp.model.Employee)}.
	 */
	@Test
	@Ignore
	public final void testDeleteEmployee() {
		fail("Not yet implemented"); // TODO
	}
}
