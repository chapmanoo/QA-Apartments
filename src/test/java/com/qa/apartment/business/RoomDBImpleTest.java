package com.qa.apartment.business;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qa.apartment.persistance.Apartment;
import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;

@RunWith(MockitoJUnitRunner.class)
public class RoomDBImpleTest {

	private String testJsonStringA;
	private String testJsonStringB;
	private Room aRoom;
	private Room bRoom;
	private Apartment aApartment;
	private Apartment bApartment;

	@InjectMocks
	private RoomImpl impl;

	@Mock
	private JSONUtil util;

	@Before
	public void setUp() {

		impl.setUtil(util);

		testJsonStringA = "{\"roomId\": \"0\",\"apartment\": \"aApartment\"}";
		testJsonStringB = "{\"roomId\": \"1\",\"apartment\": \"bApartment\"}";

		aRoom = new Room(0L, aApartment);
		bRoom = new Room(1L, bApartment);

	}

	@Test
	public void testCreateRoomFromString() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);
		Mockito.verify(util).getObjectForJSON(testJsonStringA, Room.class);

	}

	@Test
	public void testCreateRoomFromRoom() {
		String returnedString = impl.createRoomFromRoom(aRoom);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);
	}

	@Test
	public void testUpdateRoomFromString() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);
		Mockito.when(util.getObjectForJSON(testJsonStringB, Room.class)).thenReturn(bRoom);

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		returnedString = impl.updateRoomFromString(1L, testJsonStringB);
		Assert.assertEquals("{\"message\": \"room sucessfully updated\"}", returnedString);

	}

	@Test
	public void testUpdateRoomFromRoom() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);
		Mockito.when(util.getObjectForJSON(testJsonStringB, Room.class)).thenReturn(bRoom);

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		returnedString = impl.updateRoomFromString(1L, testJsonStringB);
		Assert.assertEquals("{\"message\": \"room sucessfully updated\"}", returnedString);

	}

	@Test
	public void testDeleteRoom() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		returnedString = impl.deleteRoom(1L);
		Assert.assertEquals("{\"message\": \"room sucessfully deleted\"}", returnedString);
	}

	@Test
	public void testFindAllRooms() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);
		Mockito.when(util.getObjectForJSON(testJsonStringB, Room.class)).thenReturn(bRoom);

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		returnedString = impl.createRoomFromString(testJsonStringB);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		returnedString = impl.findAllRooms();
		HashMap<Long, Room> expectedRooms = new HashMap<Long, Room>();
		expectedRooms.put(1L, aRoom);
		expectedRooms.put(2L, bRoom);
		Assert.assertEquals(returnedString, util.getJSONForObject(expectedRooms));
	}

	@Test
	public void testFindRoom() {

		Mockito.when(util.getObjectForJSON(testJsonStringA, Room.class)).thenReturn(aRoom);

		Assert.assertNull(impl.findRoom(1L));

		String returnedString = impl.createRoomFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"room sucessfully added\"}", returnedString);

		Assert.assertEquals(impl.findRoom(1L), aRoom);

	}

}
