package com.qa.apartment.persistance;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	String apartmentNo;
	String building;
	String street;
	String city;
	String postCode;
	
}
