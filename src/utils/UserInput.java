package utils;

import java.time.LocalDate;

import main.AppManager;

//TODO Need to refactor and clean the functions.
public class UserInput {

	public String setName(String name) {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter your " + name + " name: ");
			input = ScannerInputs.scanner.next().trim();
			if (checkForNumbers(input))
				isValid = true;
			else
				System.out.println("No numbers allowed!, try again");
			ScannerInputs.scanner.nextLine();
		}
		return input;
	}

	public String setPhoneNum(AppManager app) {
		boolean isValid = false;
		String input = "";
		System.out.println("Please enter phone number: ");
		while (!isValid) {
			input = ScannerInputs.scanner.next().trim();
			if (checkForChars(input))
				isValid = true;
			else
				System.out.println("No charachters allowed!, try again");
			ScannerInputs.scanner.nextLine();

			// check if this phone number exists on the DB already
			if (isPhoneExists(app, input)) {
				System.out.println("User name already exists!, try again.");
				isValid = false;
			}
		}
		return input;
	}

	public LocalDate setBirthdate() {
		// TODO Need to refactor the functions for birthdate input! they repeat.
		int year = setBirthYear();
		int month = setBirthMonth();
		int day = setBirthDay();
		return LocalDate.of(year, month, day);
	}

	protected int setBirthYear() {
		System.out.println("Please enter your birth year: ");
		int input = 0, maxYear = 2021, minYear = 1900;
		boolean isValid = false;
		while (!isValid) {
			input = ScannerInputs.scanner.nextInt();
			if (input > maxYear || input < minYear)
				System.out.println("Year must be between 1900-2021");
			else
				isValid = true;
			ScannerInputs.scanner.nextLine();
		}
		return input;
	}

	protected int setBirthMonth() {
		System.out.println("Please enter your birth month: ");
		int input = 0, maxMonth = 12, minMonth = 1;
		boolean isValid = false;
		while (!isValid) {
			input = ScannerInputs.scanner.nextInt();
			if (input > maxMonth || input < minMonth)
				System.out.println("Month must be between 1-12");
			else
				isValid = true;
			ScannerInputs.scanner.nextLine();
		}
		return input;
	}

	protected int setBirthDay() {
		System.out.println("Please enter your birth Day: ");
		int input = 0, minDay = 1, maxDay = 31;
		boolean isValid = false;
		while (!isValid) {
			input = ScannerInputs.scanner.nextInt();
			if (input > maxDay || input < minDay)
				System.out.println("Day must be between 1-31");
			else
				isValid = true;
			ScannerInputs.scanner.nextLine();

		}
		return input;
	}

	private boolean checkForNumbers(String str) {
		if (!str.matches(".*[0-9].*"))
			return true;
		return false;
	}

	private boolean checkForChars(String str) {
		if (str.matches("^[0-9]+$"))
			return true;
		return false;
	}

	private boolean isPhoneExists(AppManager app, String str) {
		for (int i = 0; i < app.getNumOfClients(); i++) {
			if (str.equals(app.getUsers()[i].getPhone())) {
				System.out.println("Phone number already exists!");
				return true;
			}
		}
		return false;
	}

	public String setUserName(AppManager app) {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter a user name: ");
			input = ScannerInputs.scanner.nextLine();
			if (!isUserNameExist(app, input))
				isValid = true;

			if (!hasSpaces(input))
				isValid = false;
		}
		return input;
	}

	public boolean isUserNameExist(AppManager app, String str) {
		for (int i = 0; i < app.getNumOfClients(); i++) {
			if (str.equals(app.getUsers()[i].getCredentials().getUserName()))
				return true;

		}
		return false;
	}

	public boolean hasSpaces(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				System.out.println("No spaces aloud, try again");
				return false;
			}
		}
		return true;
	}

	// TODO hasChar doesnt work
	public String setPassWord() {
		boolean hasChar = false, hasNum = false;
		String input = "";
		while (!hasChar || !hasNum) {
			System.out.println("Enter password with 4-8 charchters, with at least 1 digit and 1 letter.");
			input = ScannerInputs.scanner.nextLine();
			if (input.length() > 8 || input.length() < 4 || !hasSpaces(input))
				continue;
			if (checkForChars(input))
				hasChar = true;
			if (checkForNumbers(input))
				hasNum = true;
		}
		return input;
	}

	public double setIncome() {
		boolean isValid = false;
		double income = 0;
		while (!isValid) {
			System.out.println("Please enter your monthly income: ");
			income = ScannerInputs.scanner.nextDouble();
			if (income >= 0)
				isValid = true;
			ScannerInputs.scanner.nextLine();
		}
		return income;
	}
	
	
	public String getUserName() {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter a user name: ");
			input = ScannerInputs.scanner.nextLine();
			if (!hasSpaces(input))
				isValid = false;
			isValid = true;
		}
		return input;
	}
	
}
