package com.qa.apartment.business;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.apartment.persistance.Schedule;
import com.qa.apartment.util.JSONUtil;

@Transactional(Transactional.TxType.SUPPORTS)
public class ScheduleDBImple implements ScheduleService {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	private JSONUtil util = new JSONUtil();

	@Transactional(Transactional.TxType.REQUIRED)
	public String createScheduleFromString(String schedule) {
		Schedule aSchedule = util.getObjectForJSON(schedule, Schedule.class);
		em.persist(aSchedule);
		return "{\"message\": \"schedule sucessfully added\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteSchedule(Long id) {
		em.remove(em.find(Schedule.class, id));
		return "{\"message\": \"schedule sucessfully removed\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updateSchedule(Long id, String schedule) {
		Schedule aSchedule = util.getObjectForJSON(schedule, Schedule.class);
		Schedule selectedSchedule = util.getObjectForJSON(findSchedule(id), Schedule.class);
		if (selectedSchedule != null) {
			aSchedule.setId(selectedSchedule.getId());
			em.merge(aSchedule);
			return "{\"message\": \"schedule sucessfully updated\"}";
		}
		return "{\"message\": \"schedule not updated\"}";
	}

	public String findSchedule(Long id) {
		return util.getJSONForObject(em.find(Schedule.class, id));
	}

	public String findAllSchedules() {
		TypedQuery<Schedule> query = em.createQuery("SELECT m FROM Schedule m", Schedule.class);
		Collection<Schedule> schedule = (Collection<Schedule>) query.getResultList();
		return util.getJSONForObject(schedule);
	}

}
