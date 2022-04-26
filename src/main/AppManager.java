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
	protected static AccountOwner[] users; //TODO set db and actions to new class + package
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
		String firstName = userInput.getNameFromUser("first");
		String lastName = userInput.getNameFromUser("last");
		String phone = userInput.getPhoneFromUser(this);
		LocalDate birthdate = userInput.getBirthdateFromUser();
		double income = userInput.getIncomeFromUser();
		String userName = userInput.getUsernameFromUser(this);
		String password = userInput.getPassWordFromUser();

		currUser = new AccountOwner(firstName, lastName, phone, birthdate, income, userName, password);
		users[newUserIndex++] = currUser;
		bankManager.addUserToApprove(currUser);
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
		boolean userNameValid = false;
		int passAttempet = 0;
		String userName = "";

		while (!userNameValid) {
			userName = userInput.getLoginUserName();
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
			String password = ScannerInputs.getStringFromUser();
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
