package com.Think.DataMigrator.Entity;

public class city {
	private int ID;	
	private String Name;
	private String CountryCode;
	private String District;
	private int Population;
	public city(int iD, String name, String countryCode, String district, int population) {
		super();
		ID = iD;
		Name = name;
		CountryCode = countryCode;
		District = district;
		Population = population;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getDistrict() {
		return District;
	}
	public void setDistrict(String district) {
		District = district;
	}
	public int getPopulation() {
		return Population;
	}
	public void setPopulation(int population) {
		Population = population;
	}

}
