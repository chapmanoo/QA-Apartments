package com.qa.apartment.business;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
//import org.apache.log4j.Logger;
import com.qa.apartment.persistance.Apartment;
import com.qa.apartment.util.JSONUtil;
import com.qa.apartment.util.OwensDateValidator;

@Transactional(Transactional.TxType.SUPPORTS)
public class ApartmentServiceDbImpl implements ApartmentService {

	// private static final Logger LOGGER =
	// Logger.getLogger(ApartmentServiceDbImpl.class);

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	@Inject
	private OwensDateValidator odv;

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
		boolean check = newApartment.getBreakClause().compareTo(newApartment.getLeaseStart()) >= 0;

		if (newApartment != null && isValidApartmentDates(apartment)) {
			em.persist(newApartment);
			return "{\"message\": \"Apartment sucessfully Added\", \"id\" : " + newApartment.getId() + "}";

		}

		if (!check) {
			return "{\"message\": \"Break clause must be after lease start date\"}";
		}

		return "{\"message\": \"Apartment not added\"}";

	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String deleteApartment(Long id) {
		Apartment apartment = findApartment(new Long(id));
		if (apartment != null) {
			em.remove(apartment);
			return "{\"message\": \"Apartment sucessfully removed\"}";
		}
		return "{\"message\": \"Apartment wasn't removed\"}";
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public String updateApartment(Long id, String newApartment) {
		Apartment apartment = util.getObjectForJSON(newApartment, Apartment.class);
		Apartment selectedApartment = findApartment(id);
		if (selectedApartment != null && isValidApartmentDates(newApartment)) {
			apartment.setId(selectedApartment.getId());
			selectedApartment = apartment;
			em.merge(apartment);
			return "{\"message\": \"Apartment sucessfully updated\"}";
		}

		return "{\"message\": \"Apartment failed to update\"}";
	}

	private Boolean isValidApartmentDates(String apartment) {
		String[] apartmentArray = apartment.split(",");

		// leaseStart 5, leaseEnd 6, breakClause 7
		String[] leaseStart = apartmentArray[5].split("\"");
		String[] leaseEnd = apartmentArray[6].split("\"");
		String[] breakClause = apartmentArray[7].split("\"");

		// date numbers 3
		String[] dates = leaseStart[3].split("-");
		String[] dates2 = leaseEnd[3].split("-");
		String[] dates3 = breakClause[3].split("-");

		Boolean toReturn = false;

		if (odv.checkLogic(dates) && odv.checkLogic(dates2) && odv.checkLogic(dates3))
			toReturn = true;

		return toReturn;
	}

}