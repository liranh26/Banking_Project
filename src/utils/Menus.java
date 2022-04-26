package utils;

import accounts.ActivityData;

public class Menus {

	public static void startMenu() {
		System.out.println("------------------------------");
		System.out.println("Press 1 - Login.\nPress 2 - Open new account.");
		System.out.println("------------------------------");
	}

	public static void loginMenu() {
		System.out.println("------------------------------");
		System.out.println("For login via user name enter 1. \nFor login via phone enter 2.");
		System.out.println("------------------------------");
	}

	public static void defaultMessage() {
		System.out.println("Please choose a valid option from the menu!");
	}

	public static void actionMenu() {
		System.out.println("------------------------------");
		System.out.println("Please select desired action:");
		System.out.println("1. Check Account balance\n" + "2. Produce activity report\n" + "3. Make a deposit\n"
				+ "4. Make a withdrawal\n" + "5. Transfer funds\n" + "6. Pay a bill\n" + "7. Get a loan\n"
				+ "8. Logout");
		System.out.println("------------------------------");
	}

	public static void managerActionMenu() {
		System.out.println("------------------------------");
		System.out.println("Please select desired action:");
		System.out.println("1. Check Account balance\n" + "2. Produce activity report\n" + "3. Make a deposit\n"
				+ "4. Make a withdrawal\n" + "5. Transfer funds\n" + "6. Pay a bill\n" + "7. Get loan\n" + "8. Approve users\n"
				+ "9. Logout");
		System.out.println("------------------------------");
	}

	public static void printBalance(double balance) {
		System.out.println("Your balance account is: " + balance);
	}

	public static void authCodeMessage(int authCode) {
		System.out.println("Authentication code sent to you, please enter it.");
		System.out.println("------------------------------");
		System.out.println("Your code is: " + authCode);
		System.out.println("------------------------------");
	}

	public static void depositSuccess() {
		System.out.println("------------------------------");
		System.out.println("Your deposit was recieved successfully!");
		System.out.println("------------------------------");
	}

	public static void depositFail() {
		System.out.println("Your authentication code doesn't match, try again later!");
	}

	public static void printActivity(ActivityData activity) {
		System.out.println("------------------------------");
		System.out.println("At date: " + activity.getTimeStamp() + ", you did a "
				+ activity.getActivityName().toString().toLowerCase() + ", and the balance was: "
				+ activity.getBalanceChange());

	}

	public static void printBankActivity(double balance, ActivityData activity) {
		System.out.println("------------------------------");
		System.out.println("At date: " + activity.getTimeStamp() + ", the balance was: " + balance 
				+ " the change in the balance was: " + activity.getBalanceChange());

	}

	public static void withdrawalSuccess() {
		System.out.println("------------------------------");
		System.out.println("Successfuly withdrawal the money! Enjoy.");
		System.out.println("------------------------------");
	}

	public static void billMenu() {
		System.out.println("------------------------------");
		System.out.println("Choose which bill would you like to pay from the menu.");
		System.out.println("1. Loan\n" + "2. Phone\n" + "3. Water\n" + "4. Electric\n");
		System.out.println("------------------------------");
	}

	public static void printLoanStatus(int loan, double interest, double monthPayment, double curDebt) {
		System.out.println("Your original loan amount is: " + loan + ", the interest was " + interest
				+ "\n The monthly payment is: " + monthPayment + ", and the total  debt left is: " + curDebt);
		System.out.println("------------------------------");
	}

}
