package main;

import java.time.LocalDate;

//TODO Need to refactor and clean the functions.
public class UserInput {

	protected String getName(String name) {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter your " + name + " name: ");
			input = Runner.scanner.next().trim();
			if (checkForNumbers(input))
				isValid = true;
			else
				System.out.println("No numbers allowed!, try again");
			Runner.scanner.nextLine();
		}
		return input;
	}
	
	protected String getPhoneNum(AppManager app) {
		boolean isValid = false;
		String input = "";
		System.out.println("Please enter phone number: ");
		while (!isValid) {
			input = Runner.scanner.next().trim();
			if (checkForChars(input))
				isValid = true;
			else
				System.out.println("No charachters allowed!, try again");
			Runner.scanner.nextLine();

			// check if this phone number exists on the DB already
			if (isPhoneExists(app, input))
				isValid = false;
		}
		return input;
	}

	protected LocalDate getBirthdate() {
		// TODO Need to refactor the functions for birthdate input! they repeat.
		int year = getBirthYear();
		int month = getBirthMonth();
		int day = getBirthDay();
		return LocalDate.of(year, month, day);
	}

	protected int getBirthYear() {
		System.out.println("Please enter your birth year: ");
		int input = 0, maxYear = 2021, minYear = 1900;
		boolean isValid = false;
		while (!isValid) {
			input = Runner.scanner.nextInt();
			if (input > maxYear || input < minYear)
				System.out.println("Year must be between 1900-2021");
			else
				isValid = true;
			Runner.scanner.nextLine();
		}
		return input;
	}

	protected int getBirthMonth() {
		System.out.println("Please enter your birth year: ");
		int input = 0, maxMonth = 12, minMonth = 1;
		boolean isValid = false;
		while (!isValid) {
			input = Runner.scanner.nextInt();
			if (input > maxMonth || input < minMonth)
				System.out.println("Month must be between 1-12");
			else
				isValid = true;
			Runner.scanner.nextLine();
		}
		return input;
	}

	protected int getBirthDay() {
		System.out.println("Please enter your birth Day: ");
		int input = 0, minDay = 1, maxDay = 31;
		boolean isValid = false;
		while (!isValid) {
			input = Runner.scanner.nextInt();
			if (input > maxDay || input < minDay)
				System.out.println("Day must be between 1-31");
			else
				isValid = true;
			Runner.scanner.nextLine();

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
			if (str.equals(app.users[i].getPhone())) {
				System.out.println("Phone number already exists!");
				return true;
			}
		}
		return false;
	}

	protected String getUserName(AppManager app) {
		boolean isValid = false;
		String input = "";
		while (!isValid) {
			System.out.println("Enter a user name: ");
			input = Runner.scanner.nextLine();
			if (!isUserNameExist(app, input))
				isValid = true;

			if (!hasSpaces(input))
				isValid = false;
		}
		return input;
	}

	private boolean isUserNameExist(AppManager app, String str) {
		for (int i = 0; i < app.getNumOfClients(); i++) {
			if (str.equals(app.users[i].getCredentials().getUserName())) {
				System.out.println("User name already exists!, try again.");
				return true;
			}
		}
		return false;
	}

	private boolean hasSpaces(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				System.out.println("No spaces aloud, try again");
				return false;
			}
		}
		return true;
	}
	//TODO hasChar doesnt work
	protected String getPassWord() {
		boolean hasChar = false, hasNum = false;
		String input = "";
		while (!hasChar || !hasNum) {
			System.out.println("Enter password with 4-8 charchters, with at least 1 digit and 1 letter.");
			input = Runner.scanner.nextLine();
			if (input.length() > 8 || input.length() < 4 || !hasSpaces(input))
				continue;
			if (checkForChars(input))
				hasChar = true;
			if (checkForNumbers(input))
				hasNum = true;
		}
		return input;
	}

	protected double getIncome() {
		boolean isValid = false;
		double income = 0;
		while (!isValid) {
			income = Runner.scanner.nextDouble();
			if (income >= 0)
				isValid = true;
		}
		return income;
	}

}
