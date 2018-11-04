package br.com.samsumg.challenge.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.samsumg.challenge.facade.CompanyEmployeesBalancingFacade;
import br.com.samsumg.challenge.model.Employee;

public class IntegrationTests {

	private CompanyEmployeesBalancingFacade facade = CompanyEmployeesBalancingFacade.getInstance();
	
	@Test
	public void testScenario() {
		
		String fileTeam = "team.txt";
		String fileEmployees = "employees.txt";
		facade.loadData(fileTeam, fileEmployees);
		//do assertions
		assertEquals(3, facade.getTeams().size());
		assertEquals(7, facade.getEmployees().size());
		

		facade.allocate();
		//do assertions
		facade.getTeams()
			.forEach(team -> assertTrue(team.getTeamCurrentMaturity() >= team.getTeamMinMaturity()));
		

		List<Employee> promotedEmployees = facade.promote(2);
		//do assertions
		assertEquals(promotedEmployees.size(), 2);

		facade.balance();
		
		final int excedentPointsAverage = facade.getExcedentPointsAverage();
		//do assertions
		facade.getTeams()
		.forEach(team -> assertTrue(team.getTeamCurrentMaturity() >= team.getTeamMinMaturity() 
				&& (team.getTeamExceeedingMaturity() == excedentPointsAverage || team.getTeamExceeedingMaturity() == excedentPointsAverage + 1)));
	
	

		promotedEmployees = facade.promote(4);
		//do assertions
		assertEquals(promotedEmployees.size(), 4);
	

		facade.balance();
		
		final int excedentPointsAverageAfterPromoteFour = facade.getExcedentPointsAverage();
		//do assertions
		facade.getTeams()
		.forEach(team -> assertTrue(team.getTeamCurrentMaturity() >= team.getTeamMinMaturity() 
				&& (team.getTeamExceeedingMaturity() == excedentPointsAverageAfterPromoteFour || team.getTeamExceeedingMaturity() == excedentPointsAverageAfterPromoteFour + 1)));
	
	}

}
