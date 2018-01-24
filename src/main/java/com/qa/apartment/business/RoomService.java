package com.qa.apartment.business;

import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;

public interface RoomService {

	String createRoomFromString(String roomJson);

	String createRoomFromRoom(Room room);

	String updateRoomFromString(Long id, String newDetailsJson);

	String updateRoomFromRoom(Long id, Room room);

	String deleteRoom(Long id);

	String findAllRooms();

	Room findRoom(Long id);

	void setUtil(JSONUtil util);

}
