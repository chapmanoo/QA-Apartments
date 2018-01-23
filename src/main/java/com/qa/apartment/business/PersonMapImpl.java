package com.qa.apartment.business;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.qa.apartment.persistance.Person;
import com.qa.apartment.util.JSONUtil;

@Alternative @ApplicationScoped
public class PersonMapImpl implements PersonService{

	private static final Logger LOGGER = Logger.getLogger(PersonMapImpl.class);
	private Map<Long, Person> personMap;
	private Long count = 0L;
	
	@Inject
	private JSONUtil util;
	
	public PersonMapImpl() {
		this.personMap = new HashMap<Long, Person>();
		initPerson();
	}
	
	private void initPerson() {
		Person aPerson = new Person(count,"fName","lName", "email","phoneNumber");
		personMap.put(count++, aPerson);
	}
	
	public String createPersonFromString(String person) {
		LOGGER.info("In createPersonFromString method about to add to create Person");
		Person aPerson = util.getObjectForJSON(person, Person.class);
		LOGGER.info("In createPersonFromString method about to add to map");
		personMap.put(count++, aPerson);
		return "{\"message\": \"person sucessfully added\"}";
	}

	
	public String createPersonFromPerson(Person person) {
		LOGGER.info("I createPersonFromPerson method, about to put to map");
		personMap.put(count++, person);
		return "{\"message\": \"person sucessfully added\"}";
	}

	
	public String updatePersonFromString(Long id, String newDetails) {
		LOGGER.info("In updatePersonFromString, about to get Person from personMap");
		Person oldPerson = personMap.get(id);
		LOGGER.info("about to convert newDetails to person object");
		Person newPerson = util.getObjectForJSON(newDetails, Person.class);
		LOGGER.info("if person not null");
		if(oldPerson != null) {
			LOGGER.info("old person = new person");
			oldPerson = newPerson;
			LOGGER.info("Put new details to id in map");
			personMap.put(id, oldPerson);
			return "{\"message\": \"person sucessfully updated\"}";
		}
		else {
			return "{\"message\": \"person not updated see log\"}";
		}
	}

	
	public String updatePersonFromPerson(Long id, Person newDetails) {
		Person oldPerson = personMap.get(id);
		if(oldPerson != null) {
			oldPerson = newDetails;
			personMap.put(id, oldPerson);
			return "{\"message\": \"person sucessfully updated\"}";
		}
		else {
			return "{\"message\": \"person not updated see log\"}";
		}
	}

	public String deletePerson(Long id) {
		personMap.remove(id);
		return "{\"message\": \"person sucessfully deleted\"}";
	}

	
	public String findAllPersons() {
		return util.getJSONForObject(personMap.values());
	}
	
	public Person findPerson(Long id) {
		return personMap.get(id);
	}

	public JSONUtil getUtil() {
		return util;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}
	
	public Map<Long, Person> getMap() {
		return personMap; //used to prove a bug in PersonDBImpleTest
	}
}
