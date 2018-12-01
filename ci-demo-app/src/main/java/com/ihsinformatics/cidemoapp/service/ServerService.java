/**
 * 
 */
package com.ihsinformatics.cidemoapp.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.ihsinformatics.cidemoapp.model.Employee;
import com.ihsinformatics.cidemoapp.model.Event;
import com.ihsinformatics.cidemoapp.model.Group;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * @author
 */
public class ServerService implements Service {

	// Uncomment for testing
	private static final String SERVER_ADDRESS = "localhost";
	private static final Integer SERVER_PORT = 27017;
	// Uncomment for production
	// private static final String SERVER_ADDRESS = "202.141.249.106";
	// private static final Integer SERVER_PORT = 27017;
	public String dbName = "appdb";

	private MongoClient mongoClient;
	private DB database;
	private DBCollection groups;
	private DBCollection employees;
	private DBCollection events;

	@SuppressWarnings("deprecation")
	public ServerService(String dbName) {
		this.dbName = dbName;
		mongoClient = new MongoClient(SERVER_ADDRESS, SERVER_PORT);
		database = mongoClient.getDB(dbName);
		employees = database.getCollection("employee");
		events = database.getCollection("event");
		groups = database.getCollection("group");
	}

	public String objectToString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	@Override
	public Group getGroup(String uuid) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("uuid", uuid);
		DBCursor cursor = groups.find(searchQuery);
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Group group = new Group(uuid, objectToString(obj.get("name")), objectToString(obj.get("address")),
					objectToString(obj.get("city")), objectToString(obj.get("stateOrProvince")),
					objectToString(obj.get("country")), objectToString(obj.get("postalCode")), null);
			return group;
		}
		return null;
	}

	@Override
	public List<Group> getGroups() {
		DBCursor cursor = groups.find();
		List<Group> list = new ArrayList<>();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Group group = new Group(objectToString(obj.get("uuid")), objectToString(obj.get("name")),
					objectToString(obj.get("address")), objectToString(obj.get("city")),
					objectToString(obj.get("stateOrProvince")), objectToString(obj.get("country")),
					objectToString(obj.get("postalCode")), null);
			list.add(group);
		}
		return list;
	}

	@Override
	public Group getGroupByName(String name) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", name);
		DBCursor cursor = groups.find(searchQuery);
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Group group = new Group(objectToString(obj.get("uuid")), name, objectToString(obj.get("address")),
					objectToString(obj.get("city")), objectToString(obj.get("stateOrProvince")),
					objectToString(obj.get("country")), objectToString(obj.get("postalCode")), null);
			return group;
		}
		return null;
	}

	@Override
	public Group saveGroup(Group group) {
		BasicDBObject document = new BasicDBObject();
		document.put("uuid", group.getUuid());
		document.put("name", group.getName());
		document.put("address", group.getAddress());
		document.put("city", group.getCity());
		document.put("stateOrProvince", group.getStateOrProvince());
		document.put("country", group.getCountry());
		document.put("postalCode", group.getPostalCode());
		groups.insert(document);
		return group;
	}

	@Override
	public Group updateGroup(Group group) {
		BasicDBObject query = new BasicDBObject();
		query.put("uuid", group.getUuid());
		BasicDBObject newGroup = new BasicDBObject();
		newGroup.put("name", group.getName());
		newGroup.put("address", group.getAddress());
		newGroup.put("city", group.getCity());
		newGroup.put("stateOrProvince", group.getStateOrProvince());
		newGroup.put("country", group.getCountry());
		newGroup.put("postalCode", group.getPostalCode());
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newGroup);
		groups.update(query, updateObject);
		return group;
	}

	@Override
	public void deleteGroup(Group group) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("uuid", group.getUuid());
		groups.remove(searchQuery);
	}

	@Override
	public Employee getEmployee(String uuid) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("uuid", uuid);
		DBCursor cursor = groups.find(searchQuery);
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Employee employee = new Employee(uuid, objectToString(obj.get("name")));
			return employee;
		}
		return null;
	}

	@Override
	public List<Employee> getEmployees() {
		DBCursor cursor = groups.find();
		List<Employee> list = new ArrayList<>();
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Employee employee = new Employee(objectToString(obj.get("uuid")), objectToString(obj.get("name")));
			list.add(employee);
		}
		return list;
	}

	@Override
	public List<Employee> getEmployeesByName(String name) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", name);
		DBCursor cursor = groups.find(searchQuery);
		List<Employee> list = new ArrayList<>();
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Employee employee = new Employee(objectToString(obj.get("uuid")), objectToString(obj.get("name")));
			list.add(employee);
		}
		return list;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		BasicDBObject document = new BasicDBObject();
		document.put("uuid", employee.getUuid());
		document.put("name", employee.getName());
		employees.insert(document);
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		BasicDBObject query = new BasicDBObject();
		query.put("uuid", employee.getUuid());
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", employee.getName());
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);
		employees.update(query, updateObject);
		return employee;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("uuid", employee.getUuid());
		employees.remove(searchQuery);
	}

	@Override
	public Event getEvent(String uuid) {
		return null;
	}

	@Override
	public List<Event> getEvents() {
		return null;
	}

	@Override
	public List<Event> getEventsByTitle(String title) {
		return null;
	}

	@Override
	public List<Event> getEvents(Instant from, Instant to) {
		return null;
	}

	@Override
	public Event saveEvent(Event event) {
		return null;
	}

	@Override
	public Event updateEvent(Event event) {
		return null;
	}

	@Override
	public void deleteEvent(Event event) {
	}

}
