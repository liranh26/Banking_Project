package utils;

import java.time.LocalDate;

import main.AppManager;

public class UserInput {

	public String getNameFromUser(String name) {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter your " + name + " name: ");
			input = ScannerInputs.getStringFromUser();
			if (!hasNumbers(input))
				isValid = true;
			else
				System.out.println("No numbers allowed!, try again");
		}
		return input;
	}

	public String getPhoneFromUser(AppManager app) {
		boolean isValid = false;
		String input = "";
		System.out.println("Please enter phone number: ");
		while (!isValid) {
			input = ScannerInputs.getStringFromUser();
			if (!hasChars(input) && !hasSpaces(input))
				isValid = true;
			else
				System.out.println("No charachters ans spaces allowed!, try again");

			// check if this phone number exists on the DB already
			if (isPhoneExists(app, input)) {
				System.out.println("User name already exists!, try again.");
				isValid = false;
			}
		}
		return input;
	}

	public LocalDate getBirthdateFromUser() {
		int year = getCustomInputFromUser(1900, 2022, "birth year");
		int month = getCustomInputFromUser(1, 12, "birth month");
		int day = getCustomInputFromUser(1, 31, "birth day");
		return LocalDate.of(year, month, day);
	}

	public int getCustomInputFromUser(int minValue, int maxValue, String type) {
		System.out.println("Please enter your " + type + ": ");
		int input = 0;
		boolean isValid = false;
		while (!isValid) {
			input = ScannerInputs.getIntFromUser();
			if (input > maxValue || input < minValue)
				System.out.println("The " + type + " must be between " + minValue + " to " + maxValue);
			else
				isValid = true;
		}
		return input;
	}

	private boolean hasNumbers(String str) {
		if (str.matches(".*[0-9].*"))
			return true;
		return false;
	}

	private boolean hasChars(String str) {
		if (str.matches(".*[a-zA-Z].*"))
			return true;
		return false;
	}

	public boolean hasSpaces(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				return true;
			}
		}
		return false;
	}

	public boolean isPhoneExists(AppManager app, String str) {
		for (int i = 0; i < AppManager.getNumOfClients(); i++) {
			if (str.equals(app.getUsers()[i].getPhone())) {
				return true;
			}
		}
		return false;
	}

	public String getPassWordFromUser() {
		boolean hasCharAndNum = false;
		String input = "";
		while (!hasCharAndNum) {
			System.out.println("Enter password with 4-8 charchters, with at least 1 digit and 1 letter.");
			input = ScannerInputs.getStringFromUser();
			if (input.length() > 8 || input.length() < 4)
				continue;
			if (hasChars(input) && hasNumbers(input))
				hasCharAndNum = true;
		}
		return input;
	}

	public double getIncomeFromUser() {
		boolean isValid = false;
		double income = 0;
		while (!isValid) {
			System.out.println("Please enter your monthly income: ");
			income = ScannerInputs.getDoubleFromUser();
			if (income >= 0)
				isValid = true;
		}
		return income;
	}

	public String getLoginUserName() {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter a user name: ");
			input = ScannerInputs.getStringFromUser();
			if (hasSpaces(input)) {
				System.out.println("No spaces aloud, try again");
				isValid = false;
			}
			isValid = true;
		}
		return input;
	}

	public String getLoginPhone() {
		boolean isValid = false;
		String input = "";
		System.out.println("Please enter phone number: ");
		while (!isValid) {
			input = ScannerInputs.getStringFromUser();
			if (!hasChars(input) && !hasSpaces(input))
				isValid = true;
			else
				System.out.println("No charachters ans spaces allowed!, try again");
		}
		return input;
	}

}
