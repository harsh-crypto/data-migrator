package com.Think.DataMigrator.Entity;

public class Country {
	private String Code;
	private String Name;
	private String Continent;
	private String Region;
	private int SurfaceArea;
	private int IndepYear;
	private int Population;
	private int LifeExpectancy;
	private int GNP;
	private int GNPOld;
	private String LocalName;
	private String GovernmentForm;
	private String HeadOfState;
	private String Capital;
	private String Code2;
	public Country(String code, String name, String continent, String region, int surfaceArea, int indepYear,
			int population, int lifeExpectancy, int gNP, int gNPOld, String localName, String governmentForm,
			String headOfState, String capital, String code2) {
		super();
		Code = code;
		Name = name;
		Continent = continent;
		Region = region;
		SurfaceArea = surfaceArea;
		IndepYear = indepYear;
		Population = population;
		LifeExpectancy = lifeExpectancy;
		GNP = gNP;
		GNPOld = gNPOld;
		LocalName = localName;
		GovernmentForm = governmentForm;
		HeadOfState = headOfState;
		Capital = capital;
		Code2 = code2;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getContinent() {
		return Continent;
	}
	public void setContinent(String continent) {
		Continent = continent;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public int getSurfaceArea() {
		return SurfaceArea;
	}
	public void setSurfaceArea(int surfaceArea) {
		SurfaceArea = surfaceArea;
	}
	public int getIndepYear() {
		return IndepYear;
	}
	public void setIndepYear(int indepYear) {
		IndepYear = indepYear;
	}
	public int getPopulation() {
		return Population;
	}
	public void setPopulation(int population) {
		Population = population;
	}
	public int getLifeExpectancy() {
		return LifeExpectancy;
	}
	public void setLifeExpectancy(int lifeExpectancy) {
		LifeExpectancy = lifeExpectancy;
	}
	public int getGNP() {
		return GNP;
	}
	public void setGNP(int gNP) {
		GNP = gNP;
	}
	public int getGNPOld() {
		return GNPOld;
	}
	public void setGNPOld(int gNPOld) {
		GNPOld = gNPOld;
	}
	public String getLocalName() {
		return LocalName;
	}
	public void setLocalName(String localName) {
		LocalName = localName;
	}
	public String getGovernmentForm() {
		return GovernmentForm;
	}
	public void setGovernmentForm(String governmentForm) {
		GovernmentForm = governmentForm;
	}
	public String getHeadOfState() {
		return HeadOfState;
	}
	public void setHeadOfState(String headOfState) {
		HeadOfState = headOfState;
	}
	public String getCapital() {
		return Capital;
	}
	public void setCapital(String capital) {
		Capital = capital;
	}
	public String getCode2() {
		return Code2;
	}
	public void setCode2(String code2) {
		Code2 = code2;
	}

	
}
