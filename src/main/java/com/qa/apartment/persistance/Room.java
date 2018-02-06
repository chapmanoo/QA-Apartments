package com.qa.apartment.persistance;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.log4j.Logger;

@Entity
public class Room {

	private static final Logger LOGGER = Logger.getLogger(Room.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;
  
	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_apartment"))
	private Apartment apartment;
	

	public Room() {
		LOGGER.info("Inside ROOM empty constructor");
	}

	public Room(Long roomId) {
		super();
		this.roomId = roomId;
		LOGGER.info("Inside ROOM constructor with only roomId");
	}

	public Room(Long roomId, Apartment apartment) {
		super();
		this.roomId = roomId;
		this.apartment = apartment;
		LOGGER.info("Inside ROOM constructor with roomId and apartment");
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
