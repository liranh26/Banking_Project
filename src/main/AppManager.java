package main;

import java.time.LocalDate;

import accounts.AccountOwner;
import accounts.BankManager;

public class AppManager {
	
	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected AccountOwner[] users;
	protected static int newUserIndex=0;
	protected BankManager bankManager;
	protected UserInput userInput;
	
	public AppManager() {
		intializeUsers();
		bankManager = new BankManager();
		users[newUserIndex++] = bankManager;
		userInput = new UserInput();
	}

	protected void printLoginMenu() {
		System.out.println("Press 1 - for login,\nPress 2 - for open new account");
	}

	protected void openAccount() {
		String firstName = userInput.getName("first");
		String lastName = userInput.getName("last");
		String phone = userInput.getPhoneNum(this);
		LocalDate birthdate = userInput.getBirthdate();
		double income = userInput.getIncome(); 
		String userName = userInput.getUserName(this);
		String password = userInput.getPassWord();

		currUser = new AccountOwner(firstName, lastName, phone, birthdate, income, userName, password);
		users[newUserIndex++] = currUser;
		bankManager.addUserToApprove(currUser);
	}
	

	
	private void intializeUsers() {
		users = new AccountOwner[numberOfUser];
	}
	
	protected int getNumOfClients() {
		return newUserIndex;
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
