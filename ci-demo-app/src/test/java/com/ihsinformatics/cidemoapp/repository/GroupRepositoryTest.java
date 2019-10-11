/**
 * 
 */
package com.ihsinformatics.cidemoapp.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ihsinformatics.cidemoapp.model.Group;
import com.mongodb.MongoClient;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@DataMongoTest
public class GroupRepositoryTest {

	@Value("${spring.data.mongodb.host}")
	private String HOST;

	@Value("${spring.data.mongodb.port}")
	private Integer PORT;

	@Value("${spring.data.mongodb.database}")
	private String DB_NAME;

	private MongoTemplate template;

	private MongoClient client;

	private static String collection = "group";

	@Autowired
	private GroupRepository groupRepository;

	@Before
	public void setup() {
		client = new MongoClient(HOST, PORT);
		template = new MongoTemplate(client, DB_NAME);
	}

	@Before
	public void reset() {
		template.createCollection(collection);
	}

	@After
	public void cleaup() {
		template.dropCollection(collection);
	}

	@Test
	public void shouldGetGroupByUuid() {
		Group dev = Group.builder().name("Developers").build();
		template.save(dev, collection);
		Group found = groupRepository.findByUuid(dev.getUuid());
		assertNotNull(found);
		assertEquals(found.getName(), dev.getName());
	}

	@Test
	public void shouldGetGroupByName() {
		Group dev = Group.builder().name("Developers").build();
		template.save(dev, collection);
		Group found = groupRepository.findByName(dev.getName());
		assertNotNull(found);
		assertEquals(found.getUuid(), dev.getUuid());
	}

	@Test
	public void shouldGetGroups() {
		Group dev = Group.builder().name("Developers").build();
		Group qa = Group.builder().name("QA").build();
		Group admin = Group.builder().name("Administration").build();
		List<Group> list = Arrays.asList(dev, qa, admin);
		template.insertAll(list);
		List<Group> found = groupRepository.findAll();
		assertEquals(list.size(), found.size());
		for (Group group : list) {
			assertThat(found, Matchers.hasItem(group));
		}
	}

	@Test
	public void shouldSaveGroup() {
		Group dev = Group.builder().name("Developers").build();
		dev = groupRepository.save(dev);
		Group found = template.findOne(Query.query(Criteria.where("name").is(dev.getName())), Group.class);
		assertNotNull(found);
		assertEquals(found.getUuid(), dev.getUuid());
	}

	@Test
	public void shouldDeleteGroup() {
		// TODO
	}
}
