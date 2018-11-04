package br.com.samsumg.challenge.facade;

import br.com.samsumg.challenge.bussinessobject.CompanyEmployeesBalancingBusiness;

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

	public void promote(int promotedEmployees) {
		companyEmployeesBalancingBusiness.promote(promotedEmployees);
	}

	public void balance() {
		companyEmployeesBalancingBusiness.balance();
	}

}
