package com.qa.apartment.business;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

//import org.apache.log4j.Logger;

import com.qa.apartment.persistance.Person;
import com.qa.apartment.util.JSONUtil;

@Transactional(Transactional.TxType.SUPPORTS)
public class PersonDBImple implements PersonService {

	// private static final Logger LOGGER = Logger.getLogger(PersonDBImple.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Transactional(Transactional.TxType.REQUIRED)
	public String createPersonFromString(String person) {
		Person aPerson = util.getObjectForJSON(person, Person.class);
		if (aPerson != null) {
			em.persist(aPerson);
			return "{\"message\": \"person successfully added.\"}";
		}
		return "{\"message\": \"person wasn't added. Person passed in was null.\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String createPersonFromPerson(Person person) {
		em.persist(person);
		return "{\"message\": \"person sucessfully added.\"}";
	} // This method is never used, hence not checking against null pointers

	@Transactional(Transactional.TxType.REQUIRED)
	public String updatePersonFromString(Long id, String newDetails) {
		Person aPerson = util.getObjectForJSON(newDetails, Person.class);
		Person currentPerson = findPerson(id);
		if (currentPerson != null) {
			aPerson.setPersonID(currentPerson.getPersonID());
			currentPerson = aPerson;
			em.merge(currentPerson);
			return "{\"message\": \"person successfully updated.\"}";
		}
		return "{\"message\": \"person not updated. ID passed in links to null person.\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updatePersonFromPerson(Long id, Person newDetails) {
		em.merge(newDetails);
		return "{\"message\": \"person sucessfully updated\"}";
	} // This method is never used, hence not checking against null pointers

	@Transactional(Transactional.TxType.REQUIRED)
	public String deletePerson(Long id) {
		Person person = findPerson(id);
		if (person != null) {
			Query deleteSchedules = em.createNativeQuery("DELETE FROM Schedule WHERE personID_ID= ?1");
			deleteSchedules.setParameter(1, findPerson(id).getPersonID()).executeUpdate();

			em.remove(findPerson(id));
			return "{\"message\": \"person successfully removed.\"}";
		}
		return "{\"message\": \"person wasn't removed. ID passed in links to null person.\"}";

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
