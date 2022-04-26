package main;

import java.time.LocalDate;
import java.time.LocalDateTime;

import accounts.AccountOwner;
import accounts.BankManager;
//import tests.TestsObjects;
import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

/**
 * This class manages the bank login and sign up users.
 * 
 * @author liran hadad
 * 
 */
public class AppManager {

	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected static AccountOwner[] users;
	protected static int newUserIndex = 0;
	protected BankManager bankManager;
	protected UserInput userInput;

	/**
	 * AppManager constructor makes a bank manager, users array with the future
	 * users to sign, and a userInput which recieves inputs from the users.
	 */
	public AppManager() {
		users = new AccountOwner[numberOfUser];
		bankManager = new BankManager();
		users[newUserIndex++] = bankManager;
		userInput = new UserInput();
	}

	/**
	 * rupApp triggers the bank application. it contains the login and open account
	 * options for the user.
	 */
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

	/**
	 * openAccount sets a new account for a user that is NOT already register. it
	 * asks the users data for the account via the userInput class.
	 */
	protected void openAccount() {
		String userName = userInput.getLoginUserName();

		if (isUserNameExist(userName)) {
			System.out.println("User already exists try login.");
			login();
		} else {
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

	/**
	 * checks if a username is already signed.
	 * 
	 * @param userName is the input for check whether the user exists in the
	 *                 database.
	 * @return true if it exists, false if not.
	 */
	public boolean isUserNameExist(String userName) {
		for (int i = 0; i < AppManager.getNumOfClients(); i++) {
			if (userName.equals(getUsers()[i].getCredentials().getUserName()))
				return true;
		}
		return false;
	}

	public static int getNumOfClients() {
		return newUserIndex;
	}

	/**
	 * login method prints menu for user to choose whether he would like to login
	 * via a username and password, or via phone and password.
	 */
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

	/**
	 * This method asks for a username and password, if the entered cardinals valid
	 * it sets currUser active for action and prints the options for the account.
	 * 
	 * In case the user entered is not register the method terminates and sends back
	 * to start menu.
	 * 
	 * In case the user entered wrong password 3 time lead to terminate and send
	 * back to start menu.
	 * 
	 */
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

	/**
	 * This method checks whether the entered password exceeds the limit. If the
	 * limit was passed it set a timer for 30 minutes cool down before another login
	 * attempt.
	 * 
	 * @param passAttempet - the number of attempts to login with password
	 * @param userName     - the user attempted to log in.
	 * @return null if the number of attempts exceeded the limit, and OK if not.
	 */
	protected String passLoginFail(int passAttempet, String userName) {
		if (passAttempet == 3) {
			System.out.println("Login failed 3 time, account locked for 30 minutes!");
			getAccountByUsername(userName).getAccount().setLoginFailure(LocalDateTime.now());
			return null;
		}
		return "OK";
	}

	/**
	 * This method gets a userName from the user, and then checks if the userName is
	 * valid for login to the system.
	 * 
	 * In case the bank manager didn't approve the account yet it will send a
	 * message and return to start menu.
	 * 
	 * Checks if the user exceed the allowed attempts to login, it will send a
	 * message and return to start menu.
	 * 
	 * @return String of valid user name or null for not valid user to login/
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

	/**
	 * This method gets a valid username and asks for the user to enter its matching
	 * password. the user have 3 attempts to insert correct password.
	 * 
	 * @param userName a valid username of account desired to login.
	 * @return the number of attempts by the user.
	 */
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

	/**
	 * This method asks for a phone number and password, if the entered cardinals
	 * valid it sets currUser active for action and prints the options for the
	 * account.
	 * 
	 * In case the user entered is not register the method terminates and sends back
	 * to start menu.
	 * 
	 * In case the user entered wrong password 3 time lead to terminate and send
	 * back to start menu.
	 * 
	 */
	public void loginViaPhone() {

		String phone = enterPhoneForLogin();
		if (phone == null)
			return;
		String userName = getAccountByPhone(phone).getCredentials().getUserName();
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

	/**
	 * This method asks for a phone number from the user, and then checks if the
	 * userName is valid for login to the system.
	 * 
	 * In case the bank manager didn't approve the account yet it will send a
	 * message and return to start menu.
	 * 
	 * Checks if the user exceed the allowed attempts to login, it will send a
	 * message and return to start menu.
	 * 
	 * @return String of valid user name or null for not valid user to login/
	 */
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

	/**
	 * This method retrieves an account owner according to the username the method
	 * received.
	 * 
	 * @param userName - valid username of an account.
	 * @return an account owner if there is one matching to the user name, or null
	 *         in case there isn't.
	 */
	private AccountOwner getAccountByUsername(String userName) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (userName.equals(users[i].getCredentials().getUserName()))
				return users[i];
		}
		return null;
	}

	/**
	 * This method retrieves an account owner according to the username the method
	 * received.
	 * 
	 * @param phone - a phone number of user account.
	 * @return an account owner if there is one matching to the user name, or null
	 *         in case there isn't.
	 */
	public static AccountOwner getAccountByPhone(String phone) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (phone.equals(users[i].getPhone()))
				return users[i];
		}
		return null;
	}

	/**
	 * 
	 * @return users - the database that holds the accounts.
	 */
	public AccountOwner[] getUsers() {
		return users;
	}

}
