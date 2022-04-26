package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import main.AppManager;
import utils.Menus;
import utils.ScannerInputs;
import utils.UserInput;

public class AccountOwner extends Person {
	protected double mounthlyIncome;
	protected Account account;
	protected Credentials credentials;
	protected BankManager bankManager;
	private final int subtractAmount = -1;

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
				produceReport();
				break;
			case 3:
				deposit();
				break;
			case 4:
				withdrawal();
				break;
			case 5:
				transfer();
				break;
			case 6:
				payBill();
				break;
			case 7:
				loan(); // TODO check works
				break;
			case 8:
				logout();
				break;
			default:
				Menus.defaultMessage();

			}
		}
	}

	// TODO log activity for all options!!!!!!!!!!!!!!

	protected void checkBalance() {
		Menus.printBalance(account.getBalance());
	}

	protected void deposit() {
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

	protected void produceReport() {

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
	}

	protected LocalDateTime getDateStratForReport() {
		UserInput userInput = new UserInput();
		int year = userInput.getCustomInputFromUser(0, 2022, "desired start year");
		int month = userInput.getCustomInputFromUser(1, 12, "desired start month");
		int day = userInput.getCustomInputFromUser(1, 31, "desired start day");
		return LocalDateTime.of(year, month, month, 0, 0);
	}

	protected void withdrawal() {
		int withdrawal = withdrawalAmount();
		if (withdrawal == 0)
			return;

		bankManager.collectFee(account.getFeeOperation());
		bankManager.account.addToBalance(withdrawal * subtractAmount);
		account.addToBalance(withdrawal * subtractAmount);
		Menus.withdrawalSuccess();
		account.setActivity(new ActivityData(ActivityName.WITHDRAWL, account.getBalance(), LocalDateTime.now()));
	}

	/**
	 * function helper for withdrawal use case.
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

	protected void transfer() {
		int transAmount = transferAmount();
		if (transAmount == 0)
			return;
		AccountOwner userRecieveTrans = phoneNumToTransfer();
		if (userRecieveTrans == null)
			return;

		bankManager.collectFee(account.getFeeOperation());
		userRecieveTrans.account.addToBalance(transAmount);
		account.addToBalance(transAmount * subtractAmount + account.getFeeOperation() * subtractAmount);
		account.setActivity(new ActivityData(ActivityName.TRANSFER, account.getBalance(), LocalDateTime.now()));
		System.out.println("Transfer Succeeded!");
	}

	/**
	 * helper function for transfer use case
	 * 
	 * @return a valid desired transfer amount.
	 */
	protected int transferAmount() {
		System.out.println("Insert transfer amount:");
		int transAmount = ScannerInputs.getIntFromUser();
		if (transAmount > 2000) {
			System.out.println("Exceeds valid amount!");
			return 0;
		}
		return transAmount;
	}

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

	protected void payBill() {
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

	protected void pay() {
		int bill = billAmount();
		if (bill == 0)
			return;
		account.addToBalance(bill * subtractAmount + account.getFeeOperation() * subtractAmount);
		bankManager.collectFee(account.getFeeOperation());
		System.out.println("Bill payed successfuly!");
		account.setActivity(new ActivityData(ActivityName.PAY_BILL, account.getBalance(), LocalDateTime.now()));
	}

	/**
	 * handler function for pay function to pay bills.
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
	 * pays a monthly amount from the loan taken.
	 */
	protected void loanReturn() {
		double monthlyAmount = account.getLoanMonthlyPayment();
		account.addToBalance(monthlyAmount * subtractAmount);
		bankManager.account.addToBalance(monthlyAmount);
		account.setLoanLeftMonths(account.getLoanLeftMonths() - 1);

	}

	/**
	 * takes a loan
	 */
	protected void loan() {
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
	}

	/**
	 * handler function for loan
	 * 
	 * @return the desired loan amount by the user.
	 */
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

	protected void logout() {
		System.out.println("GoodBye!");
	}

}
