package com.qa.apartment.business;

import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.qa.apartment.persistance.Person;
import com.qa.apartment.persistance.Room;
import com.qa.apartment.persistance.Schedule;
import com.qa.apartment.util.JSONUtil;

@RunWith(MockitoJUnitRunner.class)

public class ScheduleImpleTest {

	private String testJsonStringA;
	private String testJsonStringB;
	private String testJsonStringC;
	private Schedule aSchedule;
	private Schedule bSchedule;
	private Schedule cSchedule;
	private Person aPerson;
	private Person bPerson;
	private Room aRoom;
	private Date dateFrom;
	private Date dateTo;
	
	@InjectMocks
	ScheduleImple impl;

	@Mock
	private JSONUtil util;

	@Before
	public void setUp() {
		impl.setUtil(util);
		
		testJsonStringA = "{\"id\": \"1\",\"from_date\": \"2003-12-02\",\"to_date\": \"2005-03-10\", \"personID\": \"1\", \"roomID\": \"1\"}";
		testJsonStringB = "{\"id\": \"1\",\"from_date\": \"2003-12-02\",\"to_date\": \"2005-03-10\", \"personID\": \"3\", \"roomID\": \"2\"}";
		testJsonStringC = "{\"id\": \"2\",\"from_date\": \"2003-12-02\",\"to_date\": \"2005-03-10\", \"personID\": \"3\", \"roomID\": \"2\"}";
		
		aPerson = new Person("James", "Herbert", "test@test.com", "01234567891");
		bPerson = new Person("Jenkins", "Harlot", "test2@test.com", "01234567891");
		
		aRoom = new Room(1L);
		
		dateFrom = new Date(2003, 12, 02);
		dateTo = new Date(2005,03, 10);
		
		aSchedule = new Schedule(1L, dateFrom, dateTo, aPerson, aRoom);
		bSchedule = new Schedule(1L, dateFrom, dateTo, bPerson, aRoom);
		cSchedule = new Schedule(2L, dateFrom, dateTo, bPerson, aRoom);
	}

	@Test
	public void testCreateScheduleFromString() {
		Mockito.when(util.getObjectForJSON(testJsonStringA, Schedule.class)).thenReturn(aSchedule);

		String returnedString = impl.createScheduleFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"schedule map sucessfully added\"}", returnedString);

		Mockito.verify(util).getObjectForJSON(testJsonStringA, Schedule.class);
	}

	@Test
	public void testUpdateSchedule() {
		Mockito.when(util.getObjectForJSON(testJsonStringA, Schedule.class)).thenReturn(aSchedule);
		Mockito.when(util.getObjectForJSON(testJsonStringB, Schedule.class)).thenReturn(bSchedule);
		Mockito.when(util.getObjectForJSON(testJsonStringC, Schedule.class)).thenReturn(cSchedule);
		
		String returnedString = impl.createScheduleFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"schedule map sucessfully added\"}", returnedString);
		
		String breturnedString = impl.updateSchedule(testJsonStringB);
		Assert.assertEquals("{\"message\": \"schedule sucessfully updated\"}", breturnedString);

		String creturnedString = impl.updateSchedule(testJsonStringC);
		Assert.assertEquals("{\"message\": \"schedule not updated see log\"}", creturnedString);
	}
	
	@Test
	public void testDeleteSchedule() {
		Mockito.when(util.getObjectForJSON(testJsonStringA, Schedule.class)).thenReturn(aSchedule);
		
		String returnedString = impl.createScheduleFromString(testJsonStringA);
		Assert.assertEquals("{\"message\": \"schedule map sucessfully added\"}", returnedString);
		
		String breturnedString = impl.deleteSchedule(1L);
		Assert.assertEquals("{\"message\": \"schedule sucessfully deleted\"}", breturnedString);
	}
	
	/*
	@Test
	public void testFindSchedule() {
		Mockito.when(util.getObjectForJSON(testJsonStringA, Schedule.class)).thenReturn(aSchedule);
		Mockito.when(util.getObjectForJSON(testJsonStringB, Schedule.class)).thenReturn(bSchedule);
		
		System.out.println(testJsonStringA);
		System.out.println(aSchedule);
		
		Assert.assertNull(impl.findSchedule(0L));
		System.out.println(impl.findSchedule(0L));
		
		impl.createScheduleFromString(testJsonStringA);
		impl.createScheduleFromString(testJsonStringA);
		
		System.out.println(impl.findSchedule(0L));
		System.out.println(impl.findSchedule(1L));
		
		System.out.println(aSchedule);
		System.out.println(bSchedule);
		Assert.assertEquals(impl.findSchedule(1L), aSchedule);
	}
	*/
}
