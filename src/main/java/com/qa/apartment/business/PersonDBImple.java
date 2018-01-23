package com.qa.apartment.business;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import com.qa.apartment.persistance.Person;
import com.qa.apartment.util.JSONUtil;

@Transactional(Transactional.TxType.SUPPORTS)
public class PersonDBImple implements PersonService {
	
	private static final Logger LOGGER = Logger.getLogger(PersonDBImple.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Transactional(Transactional.TxType.REQUIRED)
	public String createPersonFromString(String person) {
		Person aPerson = util.getObjectForJSON(person, Person.class);
		if (aPerson != null) {
			em.persist(aPerson);
			return "{\"message\": \"person sucessfully added\"}";
		}
		return "{\"message\": \"person wasn't added\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String createPersonFromPerson(Person person) {
		em.persist(person);
		return "{\"message\": \"person sucessfully added\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updatePersonFromString(Long id, String newDetails) {
		Person aPerson = util.getObjectForJSON(newDetails, Person.class);
		Person currentPerson = findPerson(id);
		if (currentPerson != null) {
			LOGGER.info("Current person: " + util.getJSONForObject(currentPerson));
			LOGGER.info("New person: " + util.getJSONForObject(currentPerson));
			aPerson.setPersonID(currentPerson.getPersonID());
			currentPerson = aPerson;
			LOGGER.info("Merge data: " + util.getJSONForObject(currentPerson));
			em.merge(currentPerson);
			return "{\"message\": \"person sucessfully updated\"}";
		}
		return "{\"message\": \"person not updated\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updatePersonFromPerson(Long id, Person newDetails) {
		em.merge(newDetails);
		return "{\"message\": \"person sucessfully updated\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String deletePerson(Long id) {
		em.remove(findPerson(id));
		return "{\"message\": \"person sucessfully removed\"}";
	}

	public String findAllPersons() {
		TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p ORDER BY p.id", Person.class);
		return util.getJSONForObject(query.getResultList());
	}

	public Person findPerson(Long id) {
		return em.find(Person.class, id);
	}

	public JSONUtil getUtil() {
		return util;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
