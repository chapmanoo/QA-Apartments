package com.qa.apartment.persistance;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Address {

	@Column(length = 20)
	@NotEmpty(message = "cannot be empty")
	@Pattern(regexp = "^[A-zÀ-ÿ0-9 ]*$")
	private String street;

	@Column(length = 20)
	@NotEmpty(message = "cannot be empty")
	@Pattern(regexp = "^[A-zÀ-ÿ0-9 ]*$", message = "City cannot contain special characters")
	private String city;

	@Column(length = 9)
	@NotEmpty(message = "cannot be empty")
	@Pattern(regexp = "^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))[0-9][A-Za-z]{2})$", message = "Must be a valid UK postcode")
	private String postCode;

	public Address() {

	}

	public Address(String street, String city, String postCode) {
		super();
		this.street = street;
		this.city = city;
		this.postCode = postCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
