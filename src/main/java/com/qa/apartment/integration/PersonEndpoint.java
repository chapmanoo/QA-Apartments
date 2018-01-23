package com.qa.apartment.integration;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.qa.apartment.business.PersonDBImple;
import com.qa.apartment.persistance.Person;
import com.qa.apartment.util.JSONUtil;

@Path("/person")
@Produces("application/json")
public class PersonEndpoint {

	@Inject
	private PersonDBImple service;
	@Inject
	private JSONUtil util;

	@GET
	@Path("/json")
	public String getAllPersons() {
		String personList = service.findAllPersons();
		return personList;
	}

	@GET
	@Path("/json/{id}")
	public String getPerson(@PathParam("id") Long id) {
		Person toReturn = service.findPerson(id);
		return util.getJSONForObject(toReturn);
	}

	@POST
	@Path("/json")
	@Consumes("application/json")
	public String createPerson(String personToAdd) {
		return service.createPersonFromString(personToAdd);
	}

	@PUT
	@Path("/json/{id}")
	@Consumes("application/json")
	public String updatePerson(@PathParam("id") Long id, String newDetails) {
		return service.updatePersonFromString(id, newDetails);
	}

	@DELETE
	@Path("/json/{id}")
	public String deletePerson(@PathParam("id") Long id) {
		return service.deletePerson(id);
	}

}