package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import activity.ActivityData;
import activity.ActivityName;
import main.AppManager;
import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

/**
 * This class represent an account owner in the bank. It extends the Person
 * class, and has all the key features for an account in the bank. After login
 * to the system the menu of all the features will open.
 * 
 * 
 * @author Liran Hadad
 *
 */

public class AccountOwner extends Person {
	protected double mounthlyIncome;
	protected Account account;
	protected Credentials credentials;
	protected BankManager bankManager;
	private final int subtractAmount = -1;
	private final int maxTransferAmount = 2000;

	/**
	 * This constructor uses the Pesron class and extends it with the following fields.
	 * 
	 * @param firstName - a string with only chars.
	 * @param lastName  - a string with onlt chars.
	 * @param phone     - a unique field to this account user, and contains only
	 *                  numbers.
	 * @param birthdate - a date of birth of the user.
	 * @param income    - a number greater then zero represents the monthly income
	 *                  of the account owner.
	 * @param userName  - a unique String for this account.
	 * @param password  - a String with at least 1 char and 1 number.
	 */
	public AccountOwner(String firstName, String lastName, String phone, LocalDate birthdate, double income,
			String userName, String password) {
		super(firstName, lastName, phone, birthdate);
		setMounthlyIncome(income);
		account = null;
		credentials = new Credentials(userName, password);
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

	protected void setBankManager(BankManager bankManager) {
		this.bankManager = bankManager;
	}

//	/**
//	 * This method prints a menu with available actions for account owner. it asks
//	 * for an input from a the user to choose the desired action to do.
//	 */
//	public void actionMenu() {
//		int option = 0;
//		System.out.println("Welcome " + this.getFirstName() + " what would you like to do?");
//		while (option != 8) {
//			Menus.actionMenu();
//			option = ScannerInputs.getIntFromUser();
//			switch (option) {
//			case 1:
//				checkBalance();
//				break;
//			case 2:
//				produceReport();
//				break;
//			case 3:
//				deposit();
//				break;
//			case 4:
//				withdrawal();
//				break;
//			case 5:
//				transfer();
//				break;
//			case 6:
//				payBill();
//				break;
//			case 7:
//				loan();
//				break;
//			case 8:
//				logout();
//				break;
//			default:
//				Menus.defaultMessage();
//
//			}
//		}
//	}

	public void checkBalance() {
		Menus.printBalance(account.getBalance());
	}

	/**
	 * This method asks for a user input of the desired amount to deposit. It sends
	 * an authentication code for user if it matches it sets the amount input by the
	 * user.
	 */
	public void deposit() {
		int authCode = ScannerInputs.getAuthNum();
		Menus.authCodeMessage(authCode);
		int input = ScannerInputs.getIntFromUser();
		if (input == authCode) {
			System.out.println("Insert deposit amount:");
			double depositAmount = ScannerInputs.getDoubleFromUser();
			account.addToBalance(depositAmount - account.getFeeOperation());
			bankManager.account.addToBalance(depositAmount + account.getFeeOperation());
			account.setActivity(new ActivityData(ActivityName.DEPOSIT, account.getBalance(), LocalDateTime.now()));
			Menus.depositSuccess();
		} else
			Menus.depositFail();
	}

	/**
	 * This method produce a report with the activity of the account from a specific
	 * day entered by the user to now.
	 */
	public void produceReport() {

		if (account.activityLog[0] == null) {
			System.out.println("No activity to present.");
			return;
		}

		LocalDateTime startDate = getDateStratForReport();

		for (int i = 0; i < account.getLogIndex(); i++) {
			long diff = ChronoUnit.HOURS.between(startDate, account.activityLog[i].getTimeStamp());
			if (diff > 0)
				Menus.printActivity(account.activityLog[i]);
		}

		if (account.getLoanLeftMonths() > 0)
			Menus.printLoanStatus(account.getLoan(), account.getInterstRate(), account.getLoanMonthlyPayment(),
					account.getTotalDebt());
		else
			System.out.println("No loan taken yet!");

	}

	protected LocalDateTime getDateStratForReport() {
		UserInput userInput = new UserInput();
		int year = userInput.getCustomInputFromUser(0, 2022, "desired start year");
		int month = userInput.getCustomInputFromUser(1, 12, "desired start month");
		int day = userInput.getCustomInputFromUser(1, 31, "desired start day");
		return LocalDateTime.of(year, month, day, 0, 0);
	}

	/**
	 * This method lets account owner to withdrawal from the account as long it not
	 * exceeds the daily limit or the max amount for the account. After the action
	 * the method update the balance of the account and the bank.
	 */
	public void withdrawal() {
		int withdrawal = withdrawalAmount();
		if (withdrawal == 0)
			return;

		feeCollection();
		bankManager.account.addToBalance(withdrawal * subtractAmount);
		account.addToBalance(withdrawal * subtractAmount);
		Menus.withdrawalSuccess();

		account.setActivity(new ActivityData(ActivityName.WITHDRAWL, account.getBalance(), LocalDateTime.now()));
		bankManager.account.setActivity(new ActivityData(ActivityName.WITHDRAWL,
				withdrawal * subtractAmount + account.getFeeOperation(), LocalDateTime.now()));
	}

	/**
	 * This method collects fee per action by the user. The fee amount is different
	 * for each account.
	 */
	protected void feeCollection() {
		bankManager.collectFee(account.getFeeOperation());
		account.addToBalance(subtractAmount * account.getFeeOperation());
	}

	/**
	 * This method asks the user for the withdrawal amount desired.
	 * 
	 * @return the desired withdrawal amount.
	 */
	protected int withdrawalAmount() {
		System.out.println("Insert withdrawal amount:");
		int withdrawal = ScannerInputs.getIntFromUser();
		if (!account.isWithdrawalAvailble(withdrawal)) {
			System.out.println("The amount entered is not allowed!");
			return 0;
		}
		return withdrawal;
	}

	/**
	 * This method transfer an amount from one account to another via a unique phone
	 * string that represents the account owner to receive the transfer. When finish
	 * it updates the balance account of the bank and users and logs the action.
	 */
	public void transfer() {
		int transAmount = transferAmount();
		if (transAmount == 0)
			return;
		AccountOwner userRecieveTrans = phoneNumToTransfer();
		if (userRecieveTrans == null)
			return;

		feeCollection();
		userRecieveTrans.account.addToBalance(transAmount);
		account.addToBalance(transAmount * subtractAmount + account.getFeeOperation() * subtractAmount);
		System.out.println("Transfer Succeeded!");

		account.setActivity(new ActivityData(ActivityName.TRANSFER, account.getBalance(), LocalDateTime.now()));
		bankManager.account
				.setActivity(new ActivityData(ActivityName.TRANSFER, account.getFeeOperation(), LocalDateTime.now()));
	}

	/**
	 * This method asks the user for the transfer amount desired.
	 * 
	 * @return a valid desired transfer amount.
	 */
	protected int transferAmount() {
		System.out.println("Insert transfer amount:");
		int transAmount = ScannerInputs.getIntFromUser();
		if (transAmount > maxTransferAmount) {
			System.out.println("Exceeds valid amount!");
			return 0;
		}
		return transAmount;
	}

	/**
	 * This method asks the user for a phone number to receive a transfer action.
	 * 
	 * @return account owner of the desired account to transfer to it.
	 */
	protected AccountOwner phoneNumToTransfer() {
		System.out.println("Insert phone number:");
		String phoneNum = ScannerInputs.getStringFromUser();
		AccountOwner userRecieveTrans = AppManager.getAccountByPhone(phoneNum);
		if (userRecieveTrans == null) {
			System.out.println("User not found!");
			return null;
		}
		return userRecieveTrans;
	}

	/**
	 * This method prints menu of types of bills available to pay, and asks the user
	 * to choose desired action.
	 */
	public void payBill() {
		Menus.billMenu();
		int option = ScannerInputs.getIntFromUser();
		switch (option) {
		case 1:
			loanReturn();
			break;
		case 2:
		case 3:
		case 4:
			pay();
			break;
		default:
			Menus.defaultMessage();
		}
	}

	/**
	 * This method allows the user to pay a bill. When finished it logs it and
	 * updates the balance account for the bank and the user.
	 * 
	 */
	protected void pay() {
		int bill = billAmount();
		if (bill == 0)
			return;

		feeCollection();
		account.addToBalance(bill * subtractAmount);
		bankManager.account.addToBalance(bill * subtractAmount);
		System.out.println("Bill payed successfuly!");

		account.setActivity(new ActivityData(ActivityName.PAY_BILL, account.getBalance(), LocalDateTime.now()));
		bankManager.account.setActivity(new ActivityData(ActivityName.PAY_BILL,
				bill * subtractAmount + account.getFeeOperation(), LocalDateTime.now()));
	}

	/**
	 * This method asks the user the desired bill amount to pay and checks it not
	 * exceeds tha valid limit.
	 * 
	 * @return the bill amount user would like to pay.
	 */
	protected int billAmount() {
		System.out.println("Enter the amount you would like to pay:");
		int bill = ScannerInputs.getIntFromUser();
		if (bill > 5000) {
			System.out.println("Not valid amount! should be less then 5000NIS");
			return 0;
		}
		return bill;
	}

	/**
	 * This method pays a monthly amount from the loan taken. When finished it
	 * updates balance accounts for the bank and the user.
	 */
	protected void loanReturn() {
		if(account.getLoanLeftMonths()==0) {
			System.out.println("No loan payment exsits!");
			return;
		}
			
		double monthlyAmount = account.getLoanMonthlyPayment();

		feeCollection();
		account.addToBalance(monthlyAmount * subtractAmount);
		bankManager.account.addToBalance(monthlyAmount);
		account.setLoanLeftMonths(account.getLoanLeftMonths() - 1);
		System.out.println("Payed a monthly payment!");

		account.setActivity(new ActivityData(ActivityName.PAY_BILL, account.getBalance(), LocalDateTime.now()));
		bankManager.account.setActivity(new ActivityData(ActivityName.PAY_BILL,
				monthlyAmount + account.getFeeOperation(), LocalDateTime.now()));
	}

	/**
	 * This method takes a loan from the bank, according to the account properties
	 * the monthly return amount is set.
	 */
	public void loan() {
		int loan = getDesiredLoan();
		if (loan == 0)
			return;

		int numOfMonths = getNumOfDesiredMonthsLoan();
		if (numOfMonths == 0)
			return;

		double monthlyAmount = account.calcMonthlyPaymentForLoan(loan, numOfMonths);
		account.setLoanMonthlyPayment(monthlyAmount);
		account.setLoanLeftMonths(numOfMonths);
		account.addToBalance(loan);
		bankManager.account.addToBalance(loan * subtractAmount);
		account.setActivity(new ActivityData(ActivityName.GET_LOAN, account.getBalance(), LocalDateTime.now()));
		bankManager.account
				.setActivity(new ActivityData(ActivityName.GET_LOAN, loan * subtractAmount, LocalDateTime.now()));
	}

	private int getDesiredLoan() {
		System.out.println("Enter desired loan amount:");
		int loan = ScannerInputs.getIntFromUser();
		if (loan > account.accountProperties.maxLoan) {
			System.out.println("Loan amount desired excceed the limit.");
			return 0;
		}
		return loan;
	}

	private int getNumOfDesiredMonthsLoan() {
		System.out.println("Enter number of monthly payments:");
		int numOfMonths = ScannerInputs.getIntFromUser();
		if (numOfMonths > 60) {
			System.out.println("Period for loan return not valid.");
			return 0;
		}
		return numOfMonths;
	}

	public void logout() {
		System.out.println("GoodBye!");
	}

}
