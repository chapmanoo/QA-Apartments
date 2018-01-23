package com.qa.apartment.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long personID;

	@Column(name = "first_name", length = 15)
	private String firstName;

	@Column(name = "last_name", length = 15)
	private String lastName;

	@Column(length = 50)
	@Email
	private String email;

	@Column(name = "phone_number", length = 11)
	private String phoneNumber;

	public Person() {

	}

	public Person(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Person(Long personID, String firstName, String lastName, String email, String phoneNumber) {
		this(firstName, lastName, email, phoneNumber);
		this.personID = personID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getPersonID() {
		return personID;
	}

}