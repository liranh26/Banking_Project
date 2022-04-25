package main;

import java.time.LocalDate;

import accounts.AccountOwner;
import accounts.BankManager;

public class AppManager {

	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected AccountOwner[] users;
	protected static int newUserIndex = 0;
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

	protected void runApp() {
		boolean shutDown = false;

		System.out.println("Welcome to AJBC Bank!");
		while (!shutDown) {
			printLoginMenu();
			int option = Runner.scanner.nextInt();
			Runner.scanner.nextLine();
			switch (option) {
			case 1:
//				app.login();
				break;
			case 2:
				openAccount();
				break;
			default:
				System.out.println("please choose 1 or 2 from the menu");
			}
		}
	}

	protected void openAccount() {
		String firstName = userInput.setName("first");
		String lastName = userInput.setName("last");
		String phone = userInput.setPhoneNum(this);
		LocalDate birthdate = userInput.setBirthdate();
		double income = userInput.setIncome();
		String userName = userInput.setUserName(this);
		String password = userInput.setPassWord();

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
		System.out.println("For login via user name enter 1. \nFor login via phone enter 2.");
		int option = Runner.scanner.nextInt();
		switch (option) {
		case 1:
			loginViaUserName();
			break;
		case 2:
			loginViaPhone();
			break;
		default:
			System.out.println("please choose 1 or 2 from the menu");
		}
	}

	public void loginViaUserName() {
		boolean userNameValid = false;
		int passAttempet = 0;
		String userName = "";

		while (!userNameValid) {
			userName = userInput.getUserName();
			if (userInput.isUserNameExist(this, userName)) {
				userNameValid = true;
				if (getAccountByUsername(userName).getAccount() == null) {
					System.out.println("Your user have not been approved yet, contact bank manager.");
					return;
				}
			}

		}

		while (passAttempet != 3) {
			String password = Runner.scanner.nextLine();
			if (password.equals(getAccountByUsername(userName).getCredentials().getPassword()))
				break;
			passAttempet++;
		}

		if (passAttempet == 3) {
			// TODO cooldown
			return;
		}

		System.out.println("Login succesed!");
		currUser = getAccountByUsername(userName);
		
		//TODO UserAction menu and options.
	}

	// overloading for login via phone
	public void loginViaPhone() {

	}
	// menu actions

	// gets a valid! user name
	private AccountOwner getAccountByUsername(String userName) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (userName.equals(users[i].getCredentials().getUserName()))
				return users[i];
		}
		return null;
	}
}
