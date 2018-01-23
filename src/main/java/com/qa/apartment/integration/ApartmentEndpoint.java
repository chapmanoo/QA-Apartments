package com.qa.apartment.integration;

import javax.ws.rs.*;
import org.apache.log4j.Logger;
import com.qa.apartment.business.ApartmentServiceDbImpl;
import com.qa.apartment.util.JSONUtil;
import javax.inject.*;

@Path("/apartment")
@Produces("application/json")
public class ApartmentEndpoint {
	private static final Logger LOGGER = Logger.getLogger(ApartmentEndpoint.class);

	@Inject
	private JSONUtil jsonUtil;

	@Inject
	private ApartmentServiceDbImpl service;

	@GET
	@Path("/json/{id}")
	public String getApartment(@PathParam("id") Long id) {
		return jsonUtil.getJSONForObject(service.findApartment(id));
	}

	@GET
	@Path("/json")
	public String getAllApartments() {
		return service.findAllApartments();
	}
	
	@POST
	@Path("/json")
	@Consumes("application/json")
	public String createApartment(String newAp) {
		LOGGER.info("in ApartmentEndpoint the value of string is  " + newAp);
		return service.createApartment(newAp);
	}
	
	@DELETE
	@Path("/json/{id}")
	public String deleteApartment(@PathParam("id") Long id) {
		return service.deleteApartment(id);
	}

	@PUT
	@Path("/json/{id}")
	@Consumes("application/json")
	public String updateApartment(String newAp, @PathParam("id") Long id) {
		return service.updateApartment(id, newAp);
	}

}
