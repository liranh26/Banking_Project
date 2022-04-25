package main;

import java.time.LocalDate;

import accounts.AccountOwner;
import accounts.BankManager;
import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

public class AppManager {

	private final int numberOfUser = 100;
	protected AccountOwner currUser;
	protected AccountOwner[] users;
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

	public int getNumOfClients() {
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
			System.out.println("Enter Password: ");
			String password = ScannerInputs.getString();
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

		// TODO UserAction class -> menu and options.
		currUser.actionMenu();
		currUser = null;
	}

	public void loginViaPhone() {
		// TODO complete this method
	}

	// gets a valid! user name
	private AccountOwner getAccountByUsername(String userName) {
		for (int i = 0; i < getNumOfClients(); i++) {
			if (userName.equals(users[i].getCredentials().getUserName()))
				return users[i];
		}
		return null;
	}

	public AccountOwner[] getUsers() {
		return users;
	}

}
