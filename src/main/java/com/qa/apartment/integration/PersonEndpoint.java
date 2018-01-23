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
import javax.ws.rs.core.Response;

import com.qa.apartment.business.PersonDBImple;
import com.qa.apartment.persistance.Person;
import com.qa.apartment.util.JSONUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/person")
@Produces("application/json")
@Api(value="/person", description = "Person services are found here")

public class PersonEndpoint {

	@Inject
	private PersonDBImple service;
	@Inject
	private JSONUtil util;

	@GET
	@Path("/json")
	@ApiResponses(value= {
			@ApiResponse(code=200, message = "OK"),
			@ApiResponse(code=500, message = "Something wrong in Server")
	})
	public Response getAllPersons() {
		String personList = service.findAllPersons();
		
		//return personList;
		return Response.status(200).entity(personList).build();
	}

	@GET
	@Path("/json/{id}")
	@ApiResponses(value= {
			@ApiResponse(code=200, message = "OK"),
			@ApiResponse(code=500, message = "Something wrong in Server")
	})
	public Response getPerson(@PathParam("id") Long id) {
		Person toReturn = service.findPerson(id);
		//return util.getJSONForObject(toReturn);
		return Response.status(200).entity(toReturn).build();
	}

	@POST
	@Path("/json")
	@Consumes("application/json")
	@ApiResponses(value= {
			@ApiResponse(code=200, message = "OK"),
			@ApiResponse(code=500, message = "Something wrong in Server")
	})
	public Response createPerson(String personToAdd) {
		//return service.createPersonFromString(personToAdd);
		return Response.status(200).entity(service.createPersonFromString(personToAdd)).build();
	}

	@PUT
	@Path("/json/{id}")
	@Consumes("application/json")
	@ApiResponses(value= {
			@ApiResponse(code=200, message = "OK"),
			@ApiResponse(code=500, message = "Something wrong in Server")
	})
	public Response updatePerson(@PathParam("id") Long id, String newDetails) {
		return Response.status(200).entity(service.updatePersonFromString(id, newDetails)).build();
	}

	@DELETE
	@Path("/json/{id}")
	@ApiResponses(value= {
			@ApiResponse(code=200, message = "OK"),
			@ApiResponse(code=500, message = "Something wrong in Server")
	})
	public Response deletePerson(@PathParam("id") Long id) {
		return Response.status(200).entity(service.deletePerson(id)).build();
	}

}