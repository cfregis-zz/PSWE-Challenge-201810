package br.com.samsumg.challenge.bussinessobject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.samsumg.challenge.model.Employee;
import br.com.samsumg.challenge.model.Team;
import br.com.samsumg.challenge.util.Commands;

public class CompanyEmployeesBalancingBusiness {
	
	private int year = 2018; 
	
	private int minRequiredTeamsTotalPoints = 0;
	
	private int currentEmployeesTotalPoints = 0;
	
	private int excedentPoints = 0;
	
	private int excedentPointsAverage = 0;
	
	private int excedentPointsRemainder = 0;
	
	private List<Team> teams = null; 
	
	private List<Employee> employees = null;
	
	public void loadData(String fileTeam, String fileEmployees) {
		 Path fileTeamPath = Paths.get(fileTeam);
		 this.teams = getTeamList(fileTeamPath);
		 
		 this.minRequiredTeamsTotalPoints = this.teams.stream()
				 .mapToInt(Team::getTeamMinMaturity)
				 .sum(); 
		 
		 Path fileEmployeesPath = Paths.get(fileEmployees);
		 this.employees = getEmployeesList(fileEmployeesPath);
		 
		 this.currentEmployeesTotalPoints = this.employees.stream()
				 .mapToInt(Employee::getPLevel)
				 .sum();
		 
		 System.out.println(Commands.LOAD + " command.");
	}

	private List<Team> getTeamList(Path filePath) {
		List<Team> loadPreviousTeamListFromFile = null;
		//read file into stream, try-with-resources
		if (filePath.toFile().exists()) {
			try (Stream<String> linesStream = Files.lines(filePath)) {
				CharSequence header = "Team Name,Team Min. Maturity";
				loadPreviousTeamListFromFile = linesStream.filter(str -> !str.contains(header))
						.map(mapLineToTeam)
						.collect(Collectors.toList());

			} catch (IOException e) {
				System.out.println("Exception raised at loadPreviousTeamListFromFile file reading. " +  e.getMessage());
			}
		}
		return loadPreviousTeamListFromFile;
	}
	
	private List<Employee> getEmployeesList(Path filePath) {
		List<Employee> loadPreviousEmployeesListFromFile = null;
		//read file into stream, try-with-resources
		if (filePath.toFile().exists()) {
			try (Stream<String> linesStream = Files.lines(filePath)) {
				CharSequence header = "Employee Name,PLevel,Birth Year,Admission Year,Last Progression Year";
				loadPreviousEmployeesListFromFile = linesStream.filter(str -> !str.contains(header))
						.map(mapLineToEmployee).collect(Collectors.toList());

			} catch (IOException e) {
				System.out.println("Exception raised at loadPreviousEmployeesListFromFile file reading. " +  e.getMessage());
			}
		}
		return loadPreviousEmployeesListFromFile;
	}
	
	private Function<String, Team> mapLineToTeam = line -> {
	    Team team = null;

	    String[] linePieces = line.split(",");

	    if(linePieces.length == 2) {
	    	team = new Team();
	    	team.setTeamName(linePieces[0]);
		    team.setTeamMinMaturity(Integer.parseInt(linePieces[1]));
	    }    
	    
	    
	    return team;
	};

	private Function<String, Employee> mapLineToEmployee = line -> {
		Employee employee = null;

	    String[] linePieces = line.split(",");
	    
	    if(linePieces.length == 5) {
	    	employee = new Employee();
	    	employee.setEmployeeName(linePieces[0]);
		    employee.setPLevel(Integer.parseInt(linePieces[1]));
		    employee.setBirthYear(Integer.parseInt(linePieces[2]));
		    employee.setAdmissionYear(Integer.parseInt(linePieces[3]));
		    employee.setLastProgressionYear(Integer.parseInt(linePieces[4]));
	    }
	    
	    return employee;
	};
	
	
	public void allocate() {
		this.excedentPoints = currentEmployeesTotalPoints - minRequiredTeamsTotalPoints;
		this.excedentPointsAverage =  this.excedentPoints/this.teams.size();
		this.excedentPointsRemainder = this.excedentPoints%this.teams.size();
		
		for (Team team : this.teams) {
			team.setEmployees(new ArrayList<>());
			team.setTeamCurrentMaturity(0);
		}
		
		Comparator<? super Employee> comparator = new Comparator<Employee>() {
			 /**
			  *  Descending order comparator by pLevel
			  * @param o1
			  * @param o2
			  * @return
			  */
	        @Override
	        public int compare(Employee o1, Employee o2) {
	            return o1.getPLevel() > o2.getPLevel() ? -1 :(o1.getPLevel() < o2.getPLevel() ? 1 : 0);
	        }
	    };
	    
		this.employees.sort(comparator);
		
		Iterator<Employee> it = this.employees.iterator();
		
	    while(it.hasNext()) {
	      Employee employee = (Employee) it.next();
	      allocateEmployeeOnClientTeams(employee);
	    }
	    
	    for (Team team : this.teams) {
			System.out.println(team.getTeamName() + " - Min. Maturity "+ team.getTeamMinMaturity() +" - Current Maturity " + team.getTeamCurrentMaturity());
	    	team.getEmployees()
	    		.forEach(e -> System.out.println(e.getEmployeeName() + " - " + e.getPLevel()));
		}

	}

	private void balanceEmployeeOnClientTeams(Employee employee) {
		for (int i = 0; i < this.teams.size(); i++) {
			Team team = this.teams.get(i);
			boolean minMaturityNotReached = team.getTeamCurrentMaturity() < team.getTeamMinMaturity()
					&& employee.getPLevel() <= team.getTeamMinMaturity()
					&& team.getTeamCurrentMaturity() + employee.getPLevel() <= team.getTeamMinMaturity()
					&& team.getTeamExceeedingMaturity() <= 0;
			
			
			boolean excedentNotReachedWithRemainder = false;
			
			if(this.excedentPointsRemainder > 0) {
				excedentNotReachedWithRemainder = (team.getTeamCurrentMaturity() + employee.getPLevel()) 
						==  team.getTeamMinMaturity() + this.excedentPointsAverage + 1;
				
			}
			
			
			boolean exceedingPointsAverageNotReached = team.getTeamCurrentMaturity() >= team.getTeamMinMaturity() 
					&& team.getTeamExceeedingMaturity() <= this.excedentPointsAverage
					&& (team.getTeamCurrentMaturity() + employee.getPLevel() ==  team.getTeamMinMaturity() + this.excedentPointsAverage ||
							excedentNotReachedWithRemainder);
			
			if(minMaturityNotReached || exceedingPointsAverageNotReached){
				if(excedentNotReachedWithRemainder) {
					this.excedentPointsRemainder--;
				}
				team.getEmployees().add(employee);
				team.setTeamCurrentMaturity(team.getTeamCurrentMaturity() + employee.getPLevel());
				break;
			} else {
				continue;
			}
		}			
	}

	private void allocateEmployeeOnClientTeams(Employee employee) {
		for (int i = 0; i < this.teams.size(); i++) {
			Team team = this.teams.get(i);
			
			boolean minMaturityNotReached = team.getTeamCurrentMaturity() < team.getTeamMinMaturity()
					&& employee.getPLevel() <= team.getTeamMinMaturity()
					&& team.getTeamCurrentMaturity() + employee.getPLevel() <= team.getTeamMinMaturity()
					&& team.getTeamExceeedingMaturity() <= 0;
			
			
			boolean pLevelBelowExceedingPoint = team.getTeamCurrentMaturity() + employee.getPLevel() <=  team.getTeamMinMaturity() + this.excedentPoints;
			boolean exceedingPointsNotReached = team.getTeamCurrentMaturity() >= team.getTeamMinMaturity() 
					&& employee.getPLevel() <= this.excedentPointsAverage + 1
					&& team.getTeamExceeedingMaturity() <= this.excedentPoints
					&& pLevelBelowExceedingPoint;

			if(minMaturityNotReached || exceedingPointsNotReached){
				if(exceedingPointsNotReached) {
					this.excedentPoints = this.excedentPoints - employee.getPLevel();
				}
				team.getEmployees().add(employee);
				team.setTeamCurrentMaturity(team.getTeamCurrentMaturity() + employee.getPLevel());
				break;
			} else {
				continue;
			}
		}			
	}

	public List<Employee> promote(int promotedEmployeesNumber) {
		List<Employee> employeesEligible = getEmployeesEligibleForPromotion();
		List<Employee> employeesPromoted = getEmployeesPromoted(employeesEligible, promotedEmployeesNumber);
		promoteEmployees(employeesPromoted);
		
		this.currentEmployeesTotalPoints = this.employees.stream()
				 .mapToInt(o -> o.getPLevel())
				 .sum();
		this.year = this.year + 1; 
		return employeesPromoted;
	}

	private void promoteEmployees(List<Employee> employeesPromoted) {
		employeesPromoted.forEach(employee -> {
			int nextLevel = employee.getPLevel() + 1;
			System.out.println(employee.getEmployeeName() + " From: " + employee.getPLevel() + " - To: " + nextLevel);
			employee.setLastProgressionYear(year);
			employee.setPLevel(nextLevel);
		});
	}

	
	/**
	 * @return eligible employess ranking points, top n selected, for promotion, following the criterias:
	 * Company time: 2 points per year
	 * Time without progression: 3 points per year
	 * Age: 1 point per 5 years old 
	 */
	private List<Employee> getEmployeesPromoted(List<Employee> employeesEligible, int promotedEmployees) {
		employeesEligible.forEach(employee -> {
			int points = 0;
			
			int pointCompanyTime = (year - employee.getAdmissionYear()) * 2;
			
			int pointTimeWithoutProgression = (year - employee.getLastProgressionYear()) * 3;
			
			int pointAge = (year - employee.getBirthYear())/5;
			
			points = pointCompanyTime + pointTimeWithoutProgression + pointAge;
			
			employee.setRankingPoints(points);
			
		});
		
		Comparator<? super Employee> comparator = new Comparator<Employee>() {
			 /**
			  *  Descending order comparator by points
			  * @param o1
			  * @param o2
			  * @return
			  */
	        @Override
	        public int compare(Employee o1, Employee o2) {
	            return o1.getRankingPoints() > o2.getRankingPoints() ? -1 :(o1.getRankingPoints() < o2.getRankingPoints() ? 1 : 0); 
	        }
	    };
	    
		employeesEligible.sort(comparator);
		
		if(employeesEligible.size() >= promotedEmployees) {
			return employeesEligible.subList(0, promotedEmployees);
		} else {
			return employeesEligible.subList(0, employeesEligible.size());
		}
				
	}

	/**
	 * 
	 * @return eligible employess following the criterias:
	 * Company time: Minimum 1 year
	 * Time without progression: Minimum 2 years, if level is 4.
	 * Age: No Restrictions.

	 */
	private List<Employee> getEmployeesEligibleForPromotion() {
		
		return employees.stream()
		 	.filter(employee -> year - employee.getAdmissionYear() > 1)
		 	.filter(employee -> employee.getPLevel() < 4 || (employee.getPLevel() == 4 && year - employee.getLastProgressionYear() >= 2))
		 	.collect(Collectors.toList());
	}

	public void balance() {
		this.excedentPoints = currentEmployeesTotalPoints - minRequiredTeamsTotalPoints;
		this.excedentPointsAverage =  this.excedentPoints/this.teams.size();
		this.excedentPointsRemainder = this.excedentPoints%this.teams.size();
		
		
		for (Team team : this.teams) {
			team.setEmployees(new ArrayList<>());
			team.setTeamCurrentMaturity(0);
		}
		
		Comparator<? super Employee> comparator = new Comparator<Employee>() {
			 /**
			  *  Descending order comparator by pLevel
			  * @param o1
			  * @param o2
			  * @return
			  */
	        @Override
	        public int compare(Employee o1, Employee o2) {
	            return o1.getPLevel() > o2.getPLevel() ? -1 :(o1.getPLevel() < o2.getPLevel() ? 1 : 0);
	        }
	    };
	    
		this.employees.sort(comparator);
		
		Iterator<Employee> it = this.employees.iterator();
		
	    while(it.hasNext()) {
	      Employee employee = (Employee) it.next();
	      balanceEmployeeOnClientTeams(employee);
	    }
	    
	    for (Team team : this.teams) {
			System.out.println(team.getTeamName() + " - Min. Maturity "+ team.getTeamMinMaturity() +" - Current Maturity " + team.getTeamCurrentMaturity());
	    	team.getEmployees()
	    		.forEach(e -> System.out.println(e.getEmployeeName() + " - " + e.getPLevel()));
		}

	}

	public int getYear() {
		return year;
	}

	public int getMinRequiredTeamsTotalPoints() {
		return minRequiredTeamsTotalPoints;
	}

	public int getCurrentEmployeesTotalPoints() {
		return currentEmployeesTotalPoints;
	}

	public int getExcedentPoints() {
		return excedentPoints;
	}

	public int getExcedentPointsAverage() {
		return excedentPointsAverage;
	}

	public int getExcedentPointsRemainder() {
		return excedentPointsRemainder;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	
}
