package br.com.samsumg.challenge.model;

/**
 * - Employees:
 * 	 Employee Name,PLevel,Birth Year,Admission Year,Last Progression Year
 */
public class Employee {
	
	private String EmployeeName;
	
	private int pLevel;
	
	private int birthYear;
	
	private int admissionYear;
	
	private int lastProgressionYear;
	
	private int rankingPoints = 0;
	

	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public int getPLevel() {
		return pLevel;
	}

	public void setPLevel(int pLevel) {
		this.pLevel = pLevel;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getAdmissionYear() {
		return admissionYear;
	}

	public void setAdmissionYear(int admissionYear) {
		this.admissionYear = admissionYear;
	}

	public int getLastProgressionYear() {
		return lastProgressionYear;
	}

	public void setLastProgressionYear(int lastProgressionYear) {
		this.lastProgressionYear = lastProgressionYear;
	}

	public int getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(int rankingPoints) {
		this.rankingPoints = rankingPoints;
	}

}
