package br.com.samsumg.challenge.app;

import java.util.Scanner;

public class CompanyEmployeesBalancingApp {

	/**
	 * Main method to run test cases
	 * @param args
	 */
	public static void main(String[] args) {
		boolean commandQuit = false;
		System.out.print(">> ");
		while(!commandQuit ) {
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			//do command, print response
			System.out.println(command);
			System.out.print(">> ");
			if(command.equalsIgnoreCase("quit")) {
				commandQuit = true;
				scanner.close();
			}
		}
		
	}
}
