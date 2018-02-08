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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import com.qa.apartment.business.ScheduleDBImple;

@Path("/schedule")
// @Produces("application/json")
public class ScheduleEndPoint {

	private static final Logger LOGGER = Logger.getLogger(ScheduleEndPoint.class);

	@Inject
	private ScheduleDBImple impl;

	@GET
	@Path("/json")
	public String getAllSchedules() {
		return impl.findAllSchedules();
	}

	@GET
	@Path("/json/{id}")
	public String getSchedule(@PathParam("id") Long id) {
		return impl.findSchedule(id);
	}

	@POST
	@Path("/json")
	// @Consumes("application/json")
	public Response addSchedule(String schedule) {
		LOGGER.info("POST on schedule started");
		String toReturn;
		ResponseBuilder response;
		try {
			toReturn = impl.createScheduleFromString(schedule);
			response = Response.status(Response.Status.OK);

		} catch (Exception e) {
			toReturn = "{\"message\": \"ALERT: Schedule not added. " + e + "\"}";
			response = Response.status(Response.Status.BAD_REQUEST);
		}
		return response.entity(toReturn).build();
	}

	@DELETE
	@Path("/json/{id}")
	public String deleteSchedule(@PathParam("id") Long id) {
		return impl.deleteSchedule(id);
	}

	@PUT
	@Path("/json/{id}")
	@Consumes("application/json")
	public Response updateSchedule(@PathParam("id") Long id, String schedule) {
		String toReturn;
		ResponseBuilder response;
		try {
			toReturn = impl.updateSchedule(id, schedule);
			response = Response.status(Response.Status.OK);
		} catch (Exception e) {
			toReturn = "{\"message\": \"ALERT: Schedule not updated. " + e + "\"}";
			response = Response.status(Response.Status.BAD_REQUEST);
		}
		return response.entity(toReturn).build();
	}

}
