package com.qa.apartment.business;

public interface ScheduleService {
		
		String findSchedule(Long id);

		String findAllSchedules();

		String createScheduleFromString(String schedule);

		String deleteSchedule(Long id);

		String updateSchedule(Long id, String schedule);
}