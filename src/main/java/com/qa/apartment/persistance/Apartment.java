package com.qa.apartment.persistance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;

import org.hibernate.validator.constraints.Range;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import com.qa.apartment.persistance.Address;

@Entity
public class Apartment {
	
	private static final Logger LOGGER = Logger.getLogger(Apartment.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 30, name = "building_name")
	@NotEmpty(message = "Building name cannot be empty")
	@Pattern(regexp = "^[A-zÀ-ÿ0-9 ]*$", message = "Building name cannot contain special characters")
	private String buildingName;

	@Column(length = 5)
	@NotEmpty(message = "Apartment name cannot be empty")
	@Pattern(regexp = "^[A-zÀ-ÿ0-9 ]*$", message = "Apartment name cannot contain special characters")
	private String apartmentNo;

	@Column(length = 25)
	@NotEmpty(message = "Agency cannot be empty")
	@Pattern(regexp = "^[A-zÀ-ÿ0-9 ]*$", message = "Agency name cannot contain special characters")
	private String agency;

	@Column(length = 30)
	@Pattern(regexp = "^[A-zÀ-ÿ ]*$", message = "Landlord cannot contain special characters or numbers")
	private String landlord;

	@Column(length = 30)
	@Pattern(regexp = "^[A-zÀ-ÿ ]*$", message = "Tenant cannot contain special characters or numbers")
	private String tenant;

	@Column(length = 10, name = "lease_start")
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date leaseStart;

	@Column(length = 10, name = "lease_end")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Future(message = "Lease end must be in the future")
	private Date leaseEnd;

	@Column(length = 10, name = "break_clause")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Future(message = "Break clause must be in the future")
	private Date breakClause;

	@Column(length = 11, name = "agency_phone_number")
	@Size(min = 11, max = 11)
	@Pattern(regexp = "^(0)([0-9])*$", message = "phone number must contain numbers only")
	private String agencyPhoneNo;
  
	@Column(length = 1, name = "number_of_rooms")
	@Range(min = 2, max = 3, message = "Apartments can have 2 or 3 rooms only")
	private Integer noRooms;

	@Column(length = 8)
	@Digits(integer = 5, fraction = 2)
	@NotNull
	private Double rent;

	@Column(length = 300)
	private String notes;

	@Column(length = 9)
	@Digits(integer = 6, fraction = 2)
	@NotNull
	private Double deposit;

	@Embedded
	@NotEmpty
	private Address addressField;
	
	@DefaultValue("true")
	private Boolean isActive;

	public Apartment() {
		LOGGER.info("Inside APARTMENT empty constructor");
	}

	public Apartment(String buildingName, String apartmentNo, String agency, String landlord, String tenant,
			Date leaseStart, Date leaseEnd, Date breakClause, String agencyPhoneNo, Integer noRooms, Double rent,
			String notes, Double deposit, Address addressObj) {
		this.buildingName = buildingName;
		this.apartmentNo = apartmentNo;
		this.agency = agency;
		this.landlord = landlord;
		this.tenant = tenant;
		this.leaseStart = leaseStart;
		this.leaseEnd = leaseEnd;
		this.breakClause = breakClause;
		this.agencyPhoneNo = agencyPhoneNo;
		this.noRooms = noRooms;
		this.rent = rent;
		this.notes = notes;
		this.deposit = deposit;
		this.addressField = addressObj;
		LOGGER.info("Inside APARTMENT constructor without ID");
	}

	public Apartment(Long id, String buildingName, String apartmentNo, String agency, String landlord, String tenant,
			Date leaseStart, Date leaseEnd, Date breakClause, String agencyPhoneNo, Integer noRooms, Double rent,
			String notes, Double deposit, Address addressObj) {
		this(buildingName, apartmentNo, agency, landlord, tenant, leaseStart, leaseEnd, breakClause, agencyPhoneNo,
				noRooms, rent, notes, deposit, addressObj);
		this.id = id;
		LOGGER.info("Inside APARTMENT constructor with ID");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getApartmentNo() {
		return apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getLandlord() {
		return landlord;
	}

	public void setLandlord(String landlord) {
		this.landlord = landlord;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public Date getLeaseStart() {
		return leaseStart;
	}

	public void setLeaseStart(Date leaseStart) {
		this.leaseStart = leaseStart;
	}

	public Date getLeaseEnd() {
		return leaseEnd;
	}

	public void setLeaseEnd(Date leaseEnd) {
		this.leaseEnd = leaseEnd;
	}

	public Date getBreakClause() {
		return breakClause;
	}

	public void setBreakClause(Date breakClause) {
		this.breakClause = breakClause;
	}

	public String getAgencyPhoneNo() {
		return agencyPhoneNo;
	}

	public void setAgencyPhoneNo(String agencyPhoneNo) {
		this.agencyPhoneNo = agencyPhoneNo;
	}

	public Integer getNoRooms() {
		return noRooms;
	}

	public void setNoRooms(Integer noRooms) {
		this.noRooms = noRooms;
	}

	public Double getRent() {
		return rent;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Address getAddressField() {
		return addressField;
	}

	public void setAddressField(Address addressField) {
		this.addressField = addressField;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
