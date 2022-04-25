package utils;

public class Menus {

	public static void startMenu() {
		System.out.println("Press 1 - for login,\nPress 2 - for open new account");
	}

	public static void loginMenu() {
		System.out.println("For login via user name enter 1. \nFor login via phone enter 2.");

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
				+ "4. Make a withdrawal\n" + "5. Transfer funds\n" + "6. Pay a bill\n" + "7. Approve users\n"
				+ "8. Logout");
		System.out.println("------------------------------");
	}

	public static void printBalance(double balance) {
		System.out.println("Your balance account is: " + balance);
	}
	
	public static void authCodeMessage(int authCode) {
		System.out.println("Authentication code sent to you, please enter it.");
		System.out.println("Your code is: "+authCode);
	}
	
	public static void depositSuccess() {
		System.out.println("Your deposit was recieved successfully!");
	}

	public static void depositFail() {
		System.out.println("Your authentication code doesn't match, try again later!");
	}

}
