package com.qa.apartment.integration;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.qa.apartment.business.RoomDBImple;
import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;

@Path("/room")
@Produces("application/json")
public class RoomEndpoint {

	@Inject
	private RoomDBImple roomService;

	@Inject
	private JSONUtil jsonUtil;

	@GET
	@Path("/json/{id}")
	public String getRoom(@PathParam("id") long id) {
		Room room = roomService.findRoom(id);
		return jsonUtil.getJSONForObject(room);
	}

	@GET
	@Path("/json")
	public String getAllRooms() {
		return roomService.findAllRooms();
	}

	@POST
	@Path("/json")
	@Consumes("application/json")
	public String createRoom(String json) {
		return roomService.createRoomFromString(json);
	}

	@DELETE
	@Path("/json/{id}")
	public String deleteRoom(@PathParam("id") long id) {
		return roomService.deleteRoom(id);
	}

}
