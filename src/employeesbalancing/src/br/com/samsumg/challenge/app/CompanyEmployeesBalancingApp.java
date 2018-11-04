package br.com.samsumg.challenge.app;

import java.util.Scanner;

import br.com.samsumg.challenge.facade.CompanyEmployeesBalancingFacade;
import br.com.samsumg.challenge.util.Commands;

public class CompanyEmployeesBalancingApp {

	/**
	 * Main method to run test cases
	 * @param args
	 */
	public static void main(String[] args) {
		boolean commandQuit = false;
		
		CompanyEmployeesBalancingFacade facade = CompanyEmployeesBalancingFacade.getInstance();
		
		//give instructions to the user
		System.out.println(">> Welcome!");
		System.out.println("These are the commands examples to use this app: ");
		System.out.println("load  \"team.txt\" \"employees.txt\" // load data from files to the system ");
		System.out.println("allocate // allocate employees between teams accordling the Maturity specified");
		System.out.println("promote 2// promote 2 employees, following promotion criterias");
		System.out.println("balance // redistribute employees between the teams, accordling the Maturity specified");
		System.out.println("Type quit, to exit the program.");
		System.out.print(">> ");
		
		//main program loop
		while(!commandQuit ) {
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			
			if(command.startsWith(Commands.LOAD)) {
				handleLoadCommand(facade, command);				
			} else if(command.equalsIgnoreCase(Commands.ALLOCATE)) {
				handleAllocateCommand(facade);
			} else if(command.startsWith(Commands.PROMOTE)) {
				handlePromoteCommand(facade, command);
			} else if(command.equalsIgnoreCase(Commands.BALANCE)) {
				handleBalanceCommand(facade);
			} else if(command.equalsIgnoreCase(Commands.QUIT)) {
				System.out.println(Commands.QUIT + " command. Bye!");
				commandQuit = true;
				scanner.close();
			} else {
				System.out.println("unknow command.");
			}
			
			//do command, print response, and then prompt
			if(!commandQuit) {
				System.out.print(">> ");
			}			
		}		
	}

	private static void handleBalanceCommand(CompanyEmployeesBalancingFacade facade) {
		facade.balance();
	}

	private static void handlePromoteCommand(CompanyEmployeesBalancingFacade facade, String command) {
		String[] commandPieces = command.split(" ");
		if(commandPieces.length == 2) {
			int promotedEmployees = Integer.parseInt(commandPieces[1]);
			facade.promote(promotedEmployees);
		} else {
			System.out.println(Commands.PROMOTE + " command bad formatted.");
		}
	}

	private static void handleAllocateCommand(CompanyEmployeesBalancingFacade facade) {
		facade.allocate();
	}

	private static void handleLoadCommand(CompanyEmployeesBalancingFacade facade, String command) {
		String commandPieces = command.replaceFirst(Commands.LOAD, "");
		int employeesFilenameStart = commandPieces.indexOf('\"');
		int employeesFilenameEnd = commandPieces.indexOf('\"', employeesFilenameStart + 1);
		
		if(employeesFilenameStart  > 0 && employeesFilenameEnd > 0) {
			String teamFilename = commandPieces.substring(employeesFilenameStart, employeesFilenameEnd +1).trim();
			String employeesFilename = commandPieces.substring(employeesFilenameEnd + 1).trim(); 
			String fileTeam = teamFilename.replaceAll("^\"|\"$", "");
			String fileEmployees = employeesFilename.replaceAll("^\"|\"$", "");
			facade.loadData(fileTeam, fileEmployees);
		} else {
			System.out.println(Commands.LOAD + " command bad formatted.");
		}
	}
}
