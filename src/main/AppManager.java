package main;

import java.time.LocalDate;
import java.util.Scanner;

import accounts.AccountOwner;
import accounts.BankManager;

public class AppManager {
	
	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected AccountOwner[] users;
	protected static int newUserIndex=0;
	protected BankManager bankManager;
	
	public AppManager() {
		intializeUsers();
		bankManager = new BankManager();
		users[newUserIndex++] = bankManager;
	}

	protected void printLoginMenu() {
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
			if(!input.matches("^[0-9]+$"))   ///////////////////////// need fix!
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
			//check if this phone number exists on the DB already
			for(int i=0; i < newUserIndex; i++) {
				if(input.equals(users[i].getPhone())) {
					isValid = false;
					System.out.println("Phone number already exists!");
				}
			}
		}
		return input;
	}

	
	private void intializeUsers() {
		users = new AccountOwner[numberOfUser];
	}
	
	
	public void login() {
		//get pass, username
		//send to overload
	}
	
	public void login(String userName, String password) {
		//check credentials
		//if its ok currUser = userName from DB
		//run option menu
	}
	
	public void login(String phone) {
		
	}
	// menu actions

}
