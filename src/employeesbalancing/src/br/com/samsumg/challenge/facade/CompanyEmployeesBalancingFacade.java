package br.com.samsumg.challenge.facade;

import java.util.List;

import br.com.samsumg.challenge.bussinessobject.CompanyEmployeesBalancingBusiness;
import br.com.samsumg.challenge.model.Employee;
import br.com.samsumg.challenge.model.Team;

public class CompanyEmployeesBalancingFacade {
	
	private static CompanyEmployeesBalancingFacade companyEmployeesBalancingFacade = null;
	
	private CompanyEmployeesBalancingBusiness companyEmployeesBalancingBusiness;
	
	public static CompanyEmployeesBalancingFacade getInstance() {
		 if(companyEmployeesBalancingFacade == null) {
	         companyEmployeesBalancingFacade = new CompanyEmployeesBalancingFacade();
	      }
	      return companyEmployeesBalancingFacade;
	}

	private CompanyEmployeesBalancingFacade() {
		companyEmployeesBalancingBusiness = new CompanyEmployeesBalancingBusiness();
	}

	public void loadData(String fileTeam, String fileEmployees) {
		companyEmployeesBalancingBusiness.loadData(fileTeam, fileEmployees);
	}

	public void allocate() {
		companyEmployeesBalancingBusiness.allocate();
	}

	public List<Employee> promote(int promotedEmployees) {
		return companyEmployeesBalancingBusiness.promote(promotedEmployees);
	}

	public void balance() {
		companyEmployeesBalancingBusiness.balance();
	}
	
	public int getYear() {
		return companyEmployeesBalancingBusiness.getYear();
	}

	public int getMinRequiredTeamsTotalPoints() {
		return companyEmployeesBalancingBusiness.getMinRequiredTeamsTotalPoints();
	}

	public int getCurrentEmployeesTotalPoints() {
		return companyEmployeesBalancingBusiness.getCurrentEmployeesTotalPoints();
	}

	public int getExcedentPoints() {
		return companyEmployeesBalancingBusiness.getExcedentPoints();
	}

	public int getExcedentPointsAverage() {
		return companyEmployeesBalancingBusiness.getExcedentPointsAverage();
	}

	public int getExcedentPointsRemainder() {
		return companyEmployeesBalancingBusiness.getExcedentPointsRemainder();
	}

	public List<Team> getTeams() {
		return companyEmployeesBalancingBusiness.getTeams();
	}

	public List<Employee> getEmployees() {
		return companyEmployeesBalancingBusiness.getEmployees();
	}

}
