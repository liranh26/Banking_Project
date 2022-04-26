package main;

import java.time.LocalDate;
import java.time.LocalDateTime;

import accounts.AccountOwner;
import accounts.BankManager;
//import tests.TestsObjects;
import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

public class AppManager {

	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected static AccountOwner[] users; // TODO set db and actions to new class + package
	protected static int newUserIndex = 0;
	protected BankManager bankManager;
	protected UserInput userInput;

	public AppManager() {
		users = new AccountOwner[numberOfUser];
		bankManager = new BankManager();
		users[newUserIndex++] = bankManager;
		userInput = new UserInput();
	}

	protected void runApp() {
		boolean shutDown = false;

		System.out.println("Welcome to AJBC Bank!");

		while (!shutDown) {
			Menus.startMenu();
			int option = ScannerInputs.getIntFromUser();
			switch (option) {
			case 1:
				login();
				break;
			case 2:
				openAccount();
				break;
			default:
				Menus.defaultMessage();
			}
		}
	}
	
//	//made up accounts for testing (import class also commented out)
//	protected void openAccount() {
//		TestsObjects test = new TestsObjects();
//		for (int i = 0; i < 3; i++) {
//			currUser = test.demoAccounts()[i];
//			users[newUserIndex++] = currUser;
//			bankManager.addUserToApprove(currUser);
//			System.out.println("For final regerstation wait for bank manager approval!");
//		}
//	}

	protected void openAccount() {
		String userName = userInput.getLoginUserName();
		
		if(isUserNameExist(userName)) {
			System.out.println("User already exists try login.");
			login();
		}
		else {
			String firstName = userInput.getNameFromUser("first");
			String lastName = userInput.getNameFromUser("last");
			String phone = userInput.getPhoneFromUser(this);
			LocalDate birthdate = userInput.getBirthdateFromUser();
			double income = userInput.getIncomeFromUser();
			String password = userInput.getPassWordFromUser();
			
			currUser = new AccountOwner(firstName, lastName, phone, birthdate, income, userName, password);
			users[newUserIndex++] = currUser;
			bankManager.addUserToApprove(currUser);
			System.out.println("For final regerstation wait for bank manager approval!");
		}
	
	}
	
	public boolean isUserNameExist(String str) {
		for (int i = 0; i < AppManager.getNumOfClients(); i++) {
			if (str.equals(getUsers()[i].getCredentials().getUserName()))
				return true;
		}
		return false;
	}
	
	
	public static int getNumOfClients() {
		return newUserIndex;
	}

	public void login() {
		Menus.loginMenu();
		int option = ScannerInputs.getIntFromUser();
		switch (option) {
		case 1:
			loginViaUserName();
			break;
		case 2:
			loginViaPhone();
			break;
		default:
			Menus.defaultMessage();
		}
	}

	public void loginViaUserName() {

		String userName = enterUsernameForLogin();
		if (userName == null)
			return;

		int passAttempet = enterPasswordForLogin(userName);

		if (passLoginFail(passAttempet, userName) == null)
			return;

		System.out.println("Login succesed!");
		currUser = getAccountByUsername(userName);

		currUser.actionMenu();
		currUser = null;
	}

	protected String passLoginFail(int passAttempet, String userName) {
		if (passAttempet == 3) {
			System.out.println("Login failed 3 time, account locked for 30 minutes!");
			getAccountByUsername(userName).getAccount().setLoginFailure(LocalDateTime.now());
			return null;
		}
		return "OK";
	}

	/**
	 * function helper for login, gets & checks valid a user name
	 * 
	 * @return String of valid user name or null for not valid user to login
	 */
	protected String enterUsernameForLogin() {
		boolean userNameValid = false;
		String userName = "";
		while (!userNameValid) {
			userName = userInput.getLoginUserName();
			if (isUserNameExist(userName)) {
				userNameValid = true;
				if (getAccountByUsername(userName).getAccount() == null) {
					System.out.println("Your user have not been approved yet, contact bank manager.");
					return null;
				}
				if (getAccountByUsername(userName).getAccount().isAccountLocked()) {
					System.out.println("Your account is locked! wait 30 minutes.");
					return null;
				}
			}
		}
		return userName;
	}

	protected int enterPasswordForLogin(String userName) {
		int passAttempet = 0;
		while (passAttempet != 3) {
			System.out.println("Enter Password: ");
			String password = ScannerInputs.getStringFromUser();
			if (password.equals(getAccountByUsername(userName).getCredentials().getPassword()))
				break;
			passAttempet++;
		}
		return passAttempet;
	}

	public void loginViaPhone() {

		String phone = enterPhoneForLogin();
		if (phone == null)
			return;
		String userName =  getAccountByPhone(phone).getCredentials().getUserName();
		// request & checks valid password by user name, need to send username via the
		// connected phone number in the account.
		int passAttempet = enterPasswordForLogin(userName);
		
		if (passLoginFail(passAttempet, userName) == null)
			return;

		System.out.println("Login succesed!");
		currUser = getAccountByPhone(phone);

		currUser.actionMenu();
		currUser = null;

	}

	protected String enterPhoneForLogin() {
		boolean phoneNumValid = false;
		String phone = "";
		while (!phoneNumValid) {
			phone = userInput.getLoginPhone();
			if (userInput.isPhoneExists(this, phone)) {
				phoneNumValid = true;
				if (getAccountByPhone(phone).getAccount() == null) {
					System.out.println("Your user have not been approved yet, contact bank manager.");
					return null;
				}
				if (getAccountByPhone(phone).getAccount().isAccountLocked()) {
					System.out.println("Your account is locked! wait 30 minutes.");
					return null;
				}
			}
		}
		return phone;
	}

	// gets a valid! user name
	private AccountOwner getAccountByUsername(String userName) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (userName.equals(users[i].getCredentials().getUserName()))
				return users[i];
		}
		return null;
	}

	public static AccountOwner getAccountByPhone(String phone) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (phone.equals(users[i].getPhone()))
				return users[i];
		}
		return null;
	}

	public AccountOwner[] getUsers() {
		return users;
	}

}
