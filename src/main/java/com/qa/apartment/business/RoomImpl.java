package com.qa.apartment.business;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;

@ApplicationScoped
@Alternative
public class RoomImpl implements RoomService {

	private static final Logger LOGGER = Logger.getLogger(RoomImpl.class);
	private final Long INITIAL_COUNT = 0L;
	private Map<Long, Room> roomMap;
	private Long ID;

	@Inject
	private JSONUtil util;

	public JSONUtil getUtil() {
		return util;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

	public RoomImpl() {
		this.roomMap = new HashMap<Long, Room>();
		ID = INITIAL_COUNT;
	}

	@Override
	public String createRoomFromString(String roomJson) {
		ID++;
		Room aRoom = util.getObjectForJSON(roomJson, Room.class);
		LOGGER.info("In createRoomFromString method about to add to roomMap");
		if (aRoom != null) {
			roomMap.put(ID, aRoom);
			LOGGER.info("In createRoomFromString method added room to roomMap");
			return "{\"message\": \"room sucessfully added\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully created. " + "Room to create is null.\"}";

	}

	@Override
	public String createRoomFromRoom(Room room) {
		ID++;
		LOGGER.info("In createRoomFromRoom method about to add to roomMap");
		if (room != null) {
			roomMap.put(ID, room);
			LOGGER.info("In createRoomFromRoom method added room to roomMap");
			return "{\"message\": \"room sucessfully added\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully created. " + "Room to create is null.\"}";

	}

	@Override
	public String updateRoomFromString(Long id, String newDetailsJson) {
		Room aRoom = util.getObjectForJSON(newDetailsJson, Room.class);
		LOGGER.info("In updateRoomFromString method about update room");
		if (aRoom != null) {
			updateRoomFromRoom(id, aRoom);
			LOGGER.info("In updateRoomFromString method sucessfully updated room");
			return "{\"message\": \"room sucessfully updated\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully updated. " + "Room passed in is null.\"}";

	}

	@Override
	public String updateRoomFromRoom(Long id, Room room) {
		Room currentRoom = roomMap.get(id);
		LOGGER.info("In updateRoomFromRoom method about to check room is not null");
		if (currentRoom != null) {
			LOGGER.info("In updateRoomFromRoom method about to update room");
			roomMap.put(id, room);
			LOGGER.info("In updateRoomFromRoom method successfully updated room");
			return "{\"message\": \"room sucessfully updated\"}";
		}
		LOGGER.info("In updateRoomFromRoom method, room is null so not updated!!");
		return "{\"message\": \"ALERT: room unsucessfully updated. " + "Room to update (currentRoom) is null.\"}";
	}

	@Override
	public String deleteRoom(Long id) {
		LOGGER.info("In deleteRoom method about delete room");
		if (roomMap.get(id) != null) {
			roomMap.remove(id);
			LOGGER.info("In deleteRoom method sucessfully deleted room");
			return "{\"message\": \"room sucessfully deleted\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully deleted. " + "Room to delete is null.\"}";

	}

	@Override
	public String findAllRooms() {
		LOGGER.info("In findAllRooms method, attempting to cast " + "HashMap roomMap to a List");
		return util.getJSONForObject(roomMap);
	}

	@Override
	public Room findRoom(Long id) {
		return roomMap.get(id);
	}

}
