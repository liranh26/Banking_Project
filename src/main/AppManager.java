package main;

import java.time.LocalDate;
import java.util.Scanner;

import accounts.AccountOwner;
import accounts.BankManager;

public class AppManager {

	protected AccountOwner currUser;
	protected AccountOwner[] users;
	protected int newUserIndex=0;
	protected BankManager bankManager;
	
	public AppManager() {
		bankManager = new BankManager();
	}

	protected void printRegisterMenu() {
		System.out.println("Press 1 - for login,/nPress 2 - for open new account");
	}

	protected void openAccount(Scanner sc) {
		String firstName = getName("first", sc);
		String lastName = getName("last", sc);
		String phone = getPhoneNum(sc);
		
//		String birthdate = sc.nextLine();
		LocalDate birthdate = LocalDate.of(2020, 5, 6);
		double income = sc.nextDouble();
		String userName = sc.nextLine();
		String password = sc.nextLine();

		currUser = new AccountOwner(firstName, lastName, phone, birthdate, income, userName, password);
		users[newUserIndex++] = currUser;
		bankManager.addUserToApprove(currUser);
	}
	
	protected String getName(String name, Scanner sc) {
		boolean isValid = false; String input="";
		while(!isValid) {
			System.out.println("Enter your "+name+" name: ");
			input = sc.next().trim();
			if(!input.matches("^[0-9]+$")) 
				isValid = true;
			else
				System.out.println("No numbers allowed!, try again");
			sc.nextLine();
		}
		return input;
	}
	
	protected String getPhoneNum(Scanner sc) {
		boolean isValid = false; String input="";
		System.out.println("Please enter phone number: ");
		while(!isValid) {
			input = sc.next().trim();
			if(input.matches("^[0-9]+$"))
				isValid = true;
			else
				System.out.println("No charachters allowed!, try again");
			sc.nextLine();
			
			for(int i=0; i < users.length; i++) {
				if(input.equals(users[i].getPhone())) {
					isValid = false;
					System.out.println("Phone number already exists!");
				}
			}
		}
		return input;
	}

	
	
	
	
	
//	protected int getIntFromUser(Scanner sc) {
//		int input = sc.nextInt();
//		return input;
//	}
//	
//	protected double getDoubleFromUser(Scanner sc) {
//		double input = sc.nextDouble();
//		return input;
//	}

}
