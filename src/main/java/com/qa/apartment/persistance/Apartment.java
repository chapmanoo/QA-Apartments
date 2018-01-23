package com.qa.apartment.persistance;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.qa.apartment.persistance.Address;

@Entity
public class Apartment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 30, name = "building_name")
	@NotNull
	private String buildingName;

	@Column(length = 5, name = "apartment_number")
	@NotNull
	private String apartmentNo;

	@Column(length = 25)
	@NotNull
	private String agency;

	@Column(length = 30)
	private String landlord;

	@Column(length = 30)
	@NotNull
	private String tenant;

	@Column(length = 10, name = "lease_start")
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date leaseStart;

	@Column(length = 10, name = "lease_end")
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date leaseEnd;

	@Column(length = 10, name = "break_clause")
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date breakClause;

	@Column(length = 11, name = "agency_phone_number")
	@NotNull
	private String agencyPhoneNo;

	@Column(length = 1, name = "number_of_rooms")
	private Integer noRooms;

	@Column(length = 8)
	@NotNull
	private Double rent;

	@Column(length = 300)
	private String notes;

	@Column(length = 175)
	@NotNull
	private String address;

	@Column(length = 9)
	@NotNull
	private Double deposit;

	@Embedded
	private Address addressField;

	public Apartment() {

	}

	public Apartment(String buildingName, String apartmentNo, String agency, String landlord, String tenant,
			Date leaseStart, Date leaseEnd, Date breakClause, String agencyPhoneNo, Integer noRooms, Double rent,
			String notes, String address, Double deposit, Address addressObj) {
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
		this.address = address;
		this.deposit = deposit;
		this.addressField = addressObj;
	}

	public Apartment(Long id, String buildingName, String apartmentNo, String agency, String landlord, String tenant,
			Date leaseStart, Date leaseEnd, Date breakClause, String agencyPhoneNo, Integer noRooms, Double rent,
			String notes, String address, Double deposit, Address addressObj) {
		this(buildingName, apartmentNo, agency, landlord, tenant, leaseStart, leaseEnd, breakClause, agencyPhoneNo,
				noRooms, rent, notes, address, deposit, addressObj);
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

}
