package com.qa.apartment.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
		
		if (aSchedule != null && isValidScheduleDates(schedule) && toAfterFrom) {
			Query getFromDate = em.createNativeQuery("SELECT FROM_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			Query getToDate = em.createNativeQuery("SELECT TO_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			getFromDate.setParameter(1, aSchedule.getPersonID());
			getToDate.setParameter(1, aSchedule.getPersonID());
			List dateFrom = getFromDate.getResultList();
			List dateTo = getToDate.getResultList();
			
			Boolean goodDate = true;
			
			for(Object datesTo: dateTo) {
				Date dF = (Date)datesTo;
				if(aSchedule.getFromDate().compareTo(dF) >= 0) {
					
				} else {
					goodDate = false;
					break;
				}
			}
			if(goodDate) {
				em.persist(aSchedule);
				return "{\"message\": \"schedule sucessfully added\"}";
			}
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
		
		if (selectedSchedule != null && isValidScheduleDates(schedule) && toAfterFrom) {
			aSchedule.setId(selectedSchedule.getId());
			
			Query getFromDate = em.createNativeQuery("SELECT FROM_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			Query getToDate = em.createNativeQuery("SELECT TO_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			getFromDate.setParameter(1, aSchedule.getPersonID());
			getToDate.setParameter(1, aSchedule.getPersonID());
			List dateFrom = getFromDate.getResultList();
			List dateTo = getToDate.getResultList();
			
			Boolean goodDate = true;
			
			for(Object datesTo: dateTo) {
				Date dF = (Date)datesTo;
				if(aSchedule.getFromDate().compareTo(dF) >= 0) {
					
				} else {
					goodDate = false;
					break;
				}
			}
			
			if(goodDate) {
				em.merge(aSchedule);
				return "{\"message\": \"schedule sucessfully updated\"}";
			}
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
