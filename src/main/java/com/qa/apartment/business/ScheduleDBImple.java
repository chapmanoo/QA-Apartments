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

		if (aSchedule != null && isValidScheduleDates(schedule)
				&& odv.checkAfterOrEqual(aSchedule.getToDate(), aSchedule.getFromDate())
				&& odv.checkAfterOrEqual(aSchedule.getFromDate(), aSchedule.getRoomID().getApartment().getLeaseStart())
				&& odv.checkAfterOrEqual(aSchedule.getRoomID().getApartment().getLeaseEnd(), aSchedule.getToDate())) {

			Query getToDate = em.createNativeQuery("SELECT TO_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			getToDate.setParameter(1, aSchedule.getPersonID());
			List dateTo = getToDate.getResultList();

			for (Object datesTo : dateTo) {
				Date dT = (Date) datesTo;
				if (!odv.checkAfterOrEqual(aSchedule.getFromDate(), dT)) {
					LOGGER.info(
							"Schedule passed validation checks. However, the schedule would overlap with a schedule before it, so has not been added.");
					return "{\"message\": \"ALERT: Schedule not added. The schedule would overlap with a schedule before it, so has not been added.\"}";
				}
			}

			em.persist(aSchedule);
			return "{\"message\": \"schedule successfully added.\"}";

		} else if (aSchedule == null) {
			LOGGER.info("Schedule failed validation checks. Schedule passed in was null.");
			return "{\"message\": \"ALERT: Schedule not added. Schedule passed in was null.\"}";
		} else if (!isValidScheduleDates(schedule)) {
			LOGGER.info("Schedule failed validation checks. Schedule has faulty dates e.g. month 15 or day 76.");
			return "{\"message\": \"ALERT: Schedule not added. Schedule has faulty dates e.g. month 15 or day 76.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getToDate(), aSchedule.getFromDate())) {
			LOGGER.info("Schedule failed validation checks. The to_date for schedule is before the from_date.");
			return "{\"message\": \"ALERT: Schedule not added. The to_date for schedule is before the from_date.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getFromDate(),
				aSchedule.getRoomID().getApartment().getLeaseStart())) {
			LOGGER.info(
					"Schedule failed validation checks. The from_date in schedule is before the lease start date for the apartment in the schedule.");
			return "{\"message\": \"ALERT: Schedule not added. The from_date in schedule is before the lease start date for the apartment in the schedule.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getRoomID().getApartment().getLeaseEnd(), aSchedule.getToDate())) {
			LOGGER.info(
					"Schedule failed validation checks. The lease end date for the apartment in the schedule is before the to_date in the schedule.");
			return "{\"message\": \"ALERT: Schedule not added. The lease end date for the apartment in the schedule is before the to_date in the schedule.\"}";
		} else {
			LOGGER.info("Schedule failed validation checks. No more detail can be given.");
			return "{\"message\": \"ALERT: Schedule not added. Schedule failed validation checks. No more detail can be given.\"}";
		}
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

		if (aSchedule != null && selectedSchedule != null && isValidScheduleDates(schedule)
				&& odv.checkAfterOrEqual(aSchedule.getToDate(), aSchedule.getFromDate())
				&& odv.checkAfterOrEqual(aSchedule.getFromDate(), aSchedule.getRoomID().getApartment().getLeaseStart())
				&& odv.checkAfterOrEqual(aSchedule.getRoomID().getApartment().getLeaseEnd(), aSchedule.getToDate())) {

			aSchedule.setId(selectedSchedule.getId());

			Query getToDate = em.createNativeQuery("SELECT TO_DATE FROM SCHEDULE WHERE PERSONID_ID = ?1");
			getToDate.setParameter(1, aSchedule.getPersonID());
			List dateTo = getToDate.getResultList();

			for (Object datesTo : dateTo) {
				Date dT = (Date) datesTo;
				if (!odv.checkAfterOrEqual(aSchedule.getFromDate(), dT)) {
					LOGGER.info(
							"Schedule passed validation checks. However, the schedule would overlap with a schedule before it, so has not been updated.");
					return "{\"message\": \"ALERT: Schedule not updated. The schedule would overlap with a schedule before it, so has not been updated.\"}";
				}
			}

			em.merge(aSchedule);
			return "{\"message\": \"schedule successfully updated\"}";

		} else if (aSchedule == null) {
			LOGGER.info("Schedule failed validation checks. Schedule passed in was null.");
			return "{\"message\": \"ALERT: Schedule not updated. Schedule passed in was null.\"}";
		} else if (selectedSchedule == null) {
			LOGGER.info("Schedule failed validation checks. Schedule at the ID given as a path parameter was null.");
			return "{\"message\": \"ALERT: Schedule not updated. Schedule passed in was null.\"}";
		} else if (!isValidScheduleDates(schedule)) {
			LOGGER.info("Schedule failed validation checks. Schedule has faulty dates e.g. month 15 or day 76.");
			return "{\"message\": \"ALERT: Schedule not updated. Schedule has faulty dates e.g. month 15 or day 76.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getToDate(), aSchedule.getFromDate())) {
			LOGGER.info("Schedule failed validation checks. The to_date for schedule is before the from_date.");
			return "{\"message\": \"ALERT: Schedule not updated. The to_date for schedule is before the from_date.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getFromDate(),
				aSchedule.getRoomID().getApartment().getLeaseStart())) {
			LOGGER.info(
					"Schedule failed validation checks. The from_date in schedule is before the lease start date for the apartment in the schedule.");
			return "{\"message\": \"ALERT: Schedule not updated. The from_date in schedule is before the lease start date for the apartment in the schedule.\"}";
		} else if (!odv.checkAfterOrEqual(aSchedule.getRoomID().getApartment().getLeaseEnd(), aSchedule.getToDate())) {
			LOGGER.info(
					"Schedule failed validation checks. The lease end date for the apartment in the schedule is before the to_date in the schedule.");
			return "{\"message\": \"ALERT: Schedule not updated. The lease end date for the apartment in the schedule is before the to_date in the schedule.\"}";
		} else {
			LOGGER.info("Schedule failed validation checks. No more detail can be given.");
			return "{\"message\": \"ALERT: Schedule not updated. Schedule failed validation checks. No more detail can be given.\"}";
		}
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
