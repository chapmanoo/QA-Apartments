package com.qa.apartment.business;


import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.qa.apartment.persistance.Apartment;
import com.qa.apartment.util.JSONUtil;

public class ApartmentServiceMapImpl implements ApartmentService {

	private final Long INITIAL_COUNT = 1L;
	private Map<Long, Apartment> apartmentMap;
	private Long ID;

	@Inject
	private JSONUtil util;

	public ApartmentServiceMapImpl() {
		this.apartmentMap = new HashMap<Long, Apartment>();
		ID = INITIAL_COUNT;
	}

	@Override
	public Apartment findApartment(Long id) {
		return apartmentMap.get(id);

	}

	@Override
	public String findAllApartments() {
		return util.getJSONForObject(apartmentMap);
	}

	@Override
	public String createApartment(String apartment) {
		Apartment newApartment = util.getObjectForJSON(apartment, Apartment.class);
		apartmentMap.put(ID, newApartment);
		return "new apartment added";// newApartment.getAddress();
	}

	@Override
	public String deleteApartment(Long id) {
		apartmentMap.remove(id);
		return null;
	}

	@Override
	public String updateApartment(Long id, String newApartmentInfo) {
		Apartment newApartment = util.getObjectForJSON(newApartmentInfo, Apartment.class);
		Long originalApartmentID = id;
		apartmentMap.put(originalApartmentID, newApartment);
		return "map updated";// apartmentMap.get(originalApartmentID).getAddress();
	}

}