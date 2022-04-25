package accounts;

import java.time.LocalDate;

import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

public class AccountOwner extends Person {
	protected double mounthlyIncome;
	protected Account account;
	protected Credentials credentials;
	// to identify finished registration option to add a flag when account approved,
	// or use != null

	public AccountOwner(String firstName, String lastName, String phone, LocalDate birthdate, double income,
			String userName, String password) {
		super(firstName, lastName, phone, birthdate);
		setMounthlyIncome(income);
		account = null;
		credentials = new Credentials(userName, password);
		System.out.println("For final regerstation wait for bank manager approval!");
	}

	public double getMounthlyIncome() {
		return mounthlyIncome;
	}

	public void setMounthlyIncome(double mounthlyIncome) {
		this.mounthlyIncome = mounthlyIncome;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void actionMenu() {
		int option = 0;
		System.out.println("Welcome " + this.getFirstName() + " what would you like to do?");
		while (option != 8) {
			Menus.actionMenu();
			option = ScannerInputs.scanner.nextInt();
			ScannerInputs.scanner.nextLine();
			switch (option) {
			case 1:
				checkBalance();
				break;
			case 2:
//				TODO Produce activity report
				break;
			case 3:
//				TODO Make a deposit
				break;
			case 4:
//				TODO Make a withdrawal
				break;
			case 5:
//				TODO Transfer funds
				break;
			case 6:
//				TODO Pay a bill
				break;
			case 7:
//				TODO Get a loan
				break;
			case 8:
//				TODO LOGOUT
				break;
			default:
				Menus.defaultMessage();

			}
		}
	}

	protected void checkBalance() {
		Menus.printBalance(account.getBalance());
		actionMenu();
	}

	protected void deposit() {
		int authCode = UserInput.getAuthNum();
		Menus.authCodeMessage(authCode);
		int input = ScannerInputs.getIntFromUser();
		if (input == authCode) {
			double depositAmount = ScannerInputs.getDoubleFromUser();
			double curBalance = account.getBalance();
			account.setBalance(depositAmount + curBalance);
			Menus.depositSuccess();
		} else
			Menus.depositFail();

		actionMenu();
	}

}
