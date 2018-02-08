package com.qa.apartment.business;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import com.qa.apartment.persistance.Apartment;
import com.qa.apartment.persistance.Room;
import com.qa.apartment.util.JSONUtil;
import com.qa.apartment.util.DateValidator;

@Transactional(Transactional.TxType.SUPPORTS)
public class ApartmentServiceDbImpl implements ApartmentService {

	private static final Logger LOGGER = Logger.getLogger(ApartmentServiceDbImpl.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Inject
	private DateValidator dv;

	public Apartment findApartment(Long id) {
		return em.find(Apartment.class, id);
	}

	public String findAllApartments() {
		TypedQuery<Apartment> query = em.createQuery("SELECT a FROM Apartment a ORDER BY a.id", Apartment.class);
		return util.getJSONForObject(query.getResultList());
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String createApartment(String apartment) {
		Apartment newApartment = util.getObjectForJSON(apartment, Apartment.class);

		if (newApartment != null && isValidApartmentDates(apartment) 
				&& dv.checkAfterOrEqual(newApartment.getBreakClause(), newApartment.getLeaseStart()) 
				&& dv.checkAfterOrEqual(newApartment.getLeaseEnd(), newApartment.getBreakClause())) {
			LOGGER.info("Apartment passed validation checks");
			em.persist(newApartment);
			return "{\"message\": \"Apartment successfully Added.\"}";
			
		} else if (!isValidApartmentDates(apartment)) {
			LOGGER.info("Apartment failed validation checks. Apartment has faulty dates e.g. month 15 or day 76.");
			return "{\"message\": \"ALERT: Apartment not added. Apartment has faulty dates e.g. month 15 or day 76.\"}";

		} else if (!dv.checkAfterOrEqual(newApartment.getBreakClause(), newApartment.getLeaseStart())) {
			LOGGER.info(
					"Apartment failed validation checks. The break clause date for apartment is before the lease start date.");
			return "{\"message\": \"ALERT: Apartment not added. The break clause date for apartment is before the lease start date.\"}";

		} else if (!dv.checkAfterOrEqual(newApartment.getLeaseEnd(), newApartment.getBreakClause())) {
			LOGGER.info(
					"Apartment failed validation checks. The lease end date for apartment is before the break clause date.");
			return "{\"message\": \"ALERT: Apartment not added. The lease end date for apartment is before the break clause date.\"}";

		} else {
			LOGGER.info("Apartment failed validation checks. Apartment passed in was null.");
			return "{\"message\": \"ALERT: Apartment not added. Apartment passed in was null.\"}";
		}
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteApartment(Long id) {
		Apartment apartment = findApartment(id);
		if (apartment != null) {
			TypedQuery<Room> roomsToDelete = em.createQuery("SELECT r FROM Room r WHERE Apartment_id = ?1", Room.class);
			List<Room> rooms = roomsToDelete.setParameter(1, apartment.getId()).getResultList();

			for (Room room : rooms) {
				Query deleteSchedules = em.createNativeQuery("DELETE FROM Schedule WHERE RoomID_roomId= ?1");
				deleteSchedules.setParameter(1, room.getRoomId()).executeUpdate();
			}

			Query deleteRooms = em.createNativeQuery("DELETE FROM ROOM WHERE Apartment_ID= ?1");
			deleteRooms.setParameter(1, apartment.getId()).executeUpdate();

			em.remove(apartment);
			return "{\"message\": \"Apartment sucessfully removed.\"}";
		}
		return "{\"message\": \"ALERT: Apartment wasn't removed. ID passed in links to null apartment.\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updateApartment(Long id, String newApartment) {
		Apartment apartment = util.getObjectForJSON(newApartment, Apartment.class);
		Apartment selectedApartment = findApartment(id);

		if (selectedApartment != null && isValidApartmentDates(newApartment) 
				&& dv.checkAfterOrEqual(apartment.getBreakClause(), apartment.getLeaseStart()) 
				&& dv.checkAfterOrEqual(apartment.getLeaseEnd(), apartment.getBreakClause())) {
			
			apartment.setId(selectedApartment.getId());
			selectedApartment = apartment;
			
			em.merge(apartment);
			return "{\"message\": \"Apartment successfully updated\"}";
			
		} else if (!isValidApartmentDates(newApartment)) {
			LOGGER.info("Apartment failed validation checks. Apartment has faulty dates e.g. month 15 or day 76.");
			return "{\"message\": \"ALERT: Apartment not updated. Apartment has faulty dates e.g. month 15 or day 76.\"}";

		} else if (!dv.checkAfterOrEqual(apartment.getBreakClause(), apartment.getLeaseStart())) {
			LOGGER.info(
					"Apartment failed validation checks. The break clause date for apartment is before the lease start date.");
			return "{\"message\": \"ALERT: Apartment not updated. The break clause date for apartment is before the lease start date.\"}";

		} else if (!dv.checkAfterOrEqual(apartment.getLeaseEnd(), apartment.getBreakClause())) {
			LOGGER.info(
					"Apartment failed validation checks. The lease end date for apartment is before the break clause date.");
			return "{\"message\": \"ALERT: Apartment not updated. The lease end date for apartment is before the break clause date.\"}";

		} else {
			LOGGER.info("Apartment failed validation checks. Apartment passed in was null.");
			return "{\"message\": \"ALERT: Apartment not added. ID passed in links to null apartment.\"}";
		}
	}

	private Boolean isValidApartmentDates(String apartment) {

		LOGGER.info("String passed in: " + apartment);
		String[] apartmentArray = apartment.split(",");

		String[] leaseStart = null;
		String[] leaseEnd = null;
		String[] breakClause = null;

		for (int i = 0; i < apartmentArray.length; i++) {
			if (apartmentArray[i].contains("leaseStart")) {
				leaseStart = apartmentArray[i].split("\"");
			}
			if (apartmentArray[i].contains("leaseEnd")) {
				leaseEnd = apartmentArray[i].split("\"");
			}
			if (apartmentArray[i].contains("breakClause")) {
				breakClause = apartmentArray[i].split("\"");
			}
		}

		// date numbers 3
		String[] dates = leaseStart[3].split("-");
		String[] dates2 = leaseEnd[3].split("-");
		String[] dates3 = breakClause[3].split("-");

		Boolean toReturn = false;

		if (dv.checkLogic(dates) && dv.checkLogic(dates2) && dv.checkLogic(dates3)) {
			LOGGER.info("All dates pass logic check");
			toReturn = true;
		} else {
			LOGGER.info("Dates failed logic check");
		}
		return toReturn;
	}

}
