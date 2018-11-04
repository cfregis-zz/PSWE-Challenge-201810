package br.com.samsumg.challenge.model;

import java.util.List;

/**
 * - Teams:
 * 	 Team Name,Team Min. Maturity
 */


public class Team {
	
	private String teamName;
	
	private int teamMinMaturity;
	
	private int teamCurrentMaturity = 0;
	
	private List<Employee> employees = null;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getTeamMinMaturity() {
		return teamMinMaturity;
	}

	public void setTeamMinMaturity(int teamMinMaturity) {
		this.teamMinMaturity = teamMinMaturity;
	}

	public int getTeamCurrentMaturity() {
		return teamCurrentMaturity;
	}

	public void setTeamCurrentMaturity(int teamCurrentMaturity) {
		this.teamCurrentMaturity = teamCurrentMaturity;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public int getTeamExceeedingMaturity() {
		if(teamCurrentMaturity > 0) {
			return teamCurrentMaturity - teamMinMaturity;
		} else {
			return 0;
		}
	}
}
