package accounts;

import java.time.LocalDate;

import utils.ScannerInputs;

public class AccountOwner extends Person {
	protected double mounthlyIncome;
	protected Account account;
	protected Credentials credentials;
	// to identify finished registration option to add a flag when account approved, or use != null 

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
		int option=0;
		System.out.println("Welcome "+this.getFirstName()+" what would you like to do?");
		while(option!=8) {
			printActionMenu();
			option = ScannerInputs.scanner.nextInt();
			ScannerInputs.scanner.nextLine();
			switch(option) {
			case 1:
//				TODO Check Account balance
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
				System.out.println("Enter valid input.");	
				
			}
		}
	}
	
	protected void printActionMenu() {
		System.out.println("Please select desired action:");
		System.out.println("1. Check Account balance\n" + "2. Produce activity report\n" + "3. Make a deposit\n" + "4. Make a withdrawal\n"
				+ "5. Transfer funds\n" + "6. Pay a bill\n" + "7. Get a loan\n" + "8. Logout");
	}
	

}
