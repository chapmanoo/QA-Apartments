package com.qa.apartment.persistance;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomId;
  
	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_apartment"))
	private Apartment apartment;

	public Room() {

	}

	public Room(Long roomId) {
		super();
		this.roomId = roomId;
	}

	public Room(Long roomId, Apartment apartment) {
		super();
		this.roomId = roomId;
		this.apartment = apartment;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
