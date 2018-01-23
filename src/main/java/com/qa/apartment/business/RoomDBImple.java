package com.qa.apartment.business;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;

@Default
@Transactional(Transactional.TxType.SUPPORTS)
public class RoomDBImple implements RoomService {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String createRoomFromString(String roomJson) {
		Room aRoom = util.getObjectForJSON(roomJson, Room.class);
		if (aRoom != null) {
			createRoomFromRoom(aRoom);
			return "{\"message\": \"room sucessfully added\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully created. " + "Room to create is null.\"}";

	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String createRoomFromRoom(Room room) {
		if (room != null) {
			em.persist(room);
			return "{\"message\": \"room sucessfully added\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully created. " + "Room to create is null.\"}";

	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String updateRoomFromString(Long id, String newDetailsJson) {
		Room aRoom = util.getObjectForJSON(newDetailsJson, Room.class);
		if (aRoom != null) {
			return updateRoomFromRoom(id, aRoom);
		}
		return "{\"message\": \"ALERT: room unsucessfully updated. " + "Room passed in is null.\"}";

	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String updateRoomFromRoom(Long id, Room room) {
		if (room != null) {
			em.merge(room);
			return "{\"message\": \"room sucessfully updated\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully updated. " + "Room passed in is null.\"}";
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteRoom(long id) {
		if (findRoom(id) != null) {
			em.remove(findRoom(id));
			return "{\"message\": \"room sucessfully deleted\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully deleted. " + "Room to delete is null.\"}";

	}

	@Override
	public String findAllRooms() {
		TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r ORDER BY r.id", Room.class);
		return util.getJSONForObject(query.getResultList());
	}

	@Override
	public Room findRoom(long id) {
		return em.find(Room.class, id);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public JSONUtil getUtil() {
		return util;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
