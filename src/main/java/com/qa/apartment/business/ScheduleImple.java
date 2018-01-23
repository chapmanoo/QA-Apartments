package com.qa.apartment.business;

import static javax.transaction.Transactional.TxType.REQUIRED;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.qa.apartment.persistance.Schedule;
import com.qa.apartment.util.JSONUtil;

@ApplicationScoped
@Alternative
public class ScheduleImple implements ScheduleService {

	private Map<Long, Schedule> scheduleList;

	private static Long INITIAL_COUNT = 0L;

	@Inject
	private JSONUtil util;

	public ScheduleImple() {
		scheduleList = new HashMap<Long, Schedule>();
	}

	@Transactional(REQUIRED)
	public String createScheduleFromString(String schedule) {
		Schedule aSchedule = util.getObjectForJSON(schedule, Schedule.class);
		scheduleList.put(INITIAL_COUNT++, aSchedule);

		return "{\"message\": \"schedule map sucessfully added\"}";
	}

	@Transactional(REQUIRED)
	public String updateSchedule(String schedule) {
		Schedule updatedSchedule = util.getObjectForJSON(schedule, Schedule.class);
		long id = updatedSchedule.getId();
		Schedule scheduleOld = scheduleList.get(id);
		if (scheduleOld != null) {
			scheduleOld = updatedSchedule;
			scheduleList.put((long) updatedSchedule.getId(), scheduleOld);
			return "{\"message\": \"schedule sucessfully updated\"}";
		}
		return "{\"message\": \"schedule not updated see log\"}";
	}

	@Transactional(REQUIRED)
	public String deleteSchedule(Long id) {
		scheduleList.remove(id);
		return "{\"message\": \"schedule sucessfully deleted\"}";
	}

	public String findAllSchedules() {
		return util.getJSONForObject(scheduleList);
	}

	public String findSchedule(Long id) {
		return util.getJSONForObject(scheduleList.get(id));
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}
}
