package com.qa.apartment.business;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import com.qa.apartment.persistance.Schedule;
import com.qa.apartment.util.JSONUtil;
import com.qa.apartment.util.DateValidator;

@Transactional(Transactional.TxType.SUPPORTS)
public class ScheduleDBImple implements ScheduleService {

	private static final Logger LOGGER = Logger.getLogger(ApartmentServiceDbImpl.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Inject
	private DateValidator odv;

	@Transactional(Transactional.TxType.REQUIRED)
	public String createScheduleFromString(String schedule) {
		LOGGER.info("Schedule string: " + schedule);
		Schedule aSchedule = util.getObjectForJSON(schedule, Schedule.class);
		Boolean toAfterFrom = aSchedule.getToDate().compareTo(aSchedule.getFromDate()) >= 0;
		Boolean fromScheduleAfterLeaseStart = aSchedule.getFromDate().compareTo(aSchedule.getRoomID().getApartment().getLeaseStart()) >= 0;
		Boolean leaseEndAfterToSchedule = aSchedule.getRoomID().getApartment().getLeaseEnd().compareTo(aSchedule.getToDate()) >= 0;
		
		
		if (aSchedule != null && isValidScheduleDates(schedule) && toAfterFrom && fromScheduleAfterLeaseStart && leaseEndAfterToSchedule) {
			em.persist(aSchedule);
			return "{\"message\": \"schedule sucessfully added\"}";
		}
		return "{\"message\": \"schedule not added\"}";
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
		Boolean toAfterFrom = aSchedule.getToDate().compareTo(aSchedule.getFromDate()) >= 0;
		Boolean fromScheduleAfterLeaseStart = aSchedule.getFromDate().compareTo(aSchedule.getRoomID().getApartment().getLeaseStart()) >= 0;
		Boolean leaseEndAfterToSchedule = aSchedule.getRoomID().getApartment().getLeaseEnd().compareTo(aSchedule.getToDate()) >= 0;
		
		
		if (selectedSchedule != null && isValidScheduleDates(schedule) && toAfterFrom && fromScheduleAfterLeaseStart && leaseEndAfterToSchedule) {
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

	private Boolean isValidScheduleDates(String schedule) {

		LOGGER.info("String passed in: " + schedule);
		String[] scheduleArray = schedule.split(",");

		String[] from_date = scheduleArray[0].split("\"");
		String[] to_date = scheduleArray[1].split("\"");

		String[] fromDateYMD = from_date[3].split("-");
		String[] toDateYMD = to_date[3].split("-");

		Boolean toReturn = false;

		if (odv.checkLogic(fromDateYMD) && odv.checkLogic(toDateYMD)) {
			LOGGER.info("Dates passed logic check");
			toReturn = true;
		}
		LOGGER.info("Dates fail logic check");
		return toReturn;
	}

}
