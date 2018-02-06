package com.qa.apartment.business;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.apartment.persistance.Apartment;
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
			
			Long appId = room.getApartmentId();
			Query q = em.createNativeQuery("SELECT COUNT(Apartment_ID) FROM ROOM WHERE Apartment_ID= ?1");
			Query q2 = em.createNativeQuery("SELECT NUMBER_OF_ROOMS FROM APARTMENT WHERE ID = ?2");
			q.setParameter(1, appId);
			q2.setParameter(2, appId);
			
			Integer rowCnt = ((Number) q.getSingleResult()).intValue();
			Integer roomCnt = ((Number) q2.getSingleResult()).intValue();
			
			if(roomCnt > rowCnt) {			
			em.persist(room);
			return "{\"message\": \"room sucessfully added\"}";
			}
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
		Room oldRoom = findRoom(id);
		if (room != null) {
			room.setRoomId(oldRoom.getRoomId());
			oldRoom = room;
			em.merge(room);
			return "{\"message\": \"room sucessfully updated\"}";
		}
		return "{\"message\": \"ALERT: room unsucessfully updated. " + "Room passed in is null.\"}";
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteRoom(Long id) {
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
	public Room findRoom(Long id) {
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
