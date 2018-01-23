package com.qa.apartment.business;
//
import com.qa.apartment.persistance.Apartment;

public interface ApartmentService {
	public Apartment findApartment(Long id);

	public String findAllApartments();

	public String createApartment(String apartment);

	public String deleteApartment(Long id);

	public String updateApartment(Long id, String newApartment);
}
