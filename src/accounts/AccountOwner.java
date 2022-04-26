package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;

import main.AppManager;
import utils.Menus;
import utils.ScannerInputs;

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
				loan();
				break;
			case 8:
				logout();
				break;
			default:
				Menus.defaultMessage();

			}
		}
	}

	//TODO log activity for all options!!!!!!!!!!!!!!
	
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
		for (int i = 0; i < account.getLogIndex(); i++) {
			Menus.printActivity(account.activityLog[i]);
		}
	}

	protected void withdrawal() {
		int withdrawal = withdrawalAmount();
		if (withdrawal == 0)
			return;

		bankManager.collectFee(account.getFeeOperation());
		bankManager.account.addToBalance(withdrawal * subtractAmount);
		account.addToBalance(withdrawal * subtractAmount);
		Menus.withdrawalSuccess();
	}

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

		System.out.println("Transfer Succeeded!");
	}

	protected int transferAmount() {
		System.out.println("Insert tranfer amount:");
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
		account.addToBalance(bill * subtractAmount);
		System.out.println("Bill payed successfuly!");
	}

	protected int billAmount() {
		System.out.println("Enter the amount you would like to pay:");
		int bill = ScannerInputs.getIntFromUser();
		if (bill > 5000) {
			System.out.println("Not valid amount! should be less then 5000NIS");
			return 0;
		}
		return bill;
	}

	protected void loanReturn() {
		double mounthlyAmount = account.getLoanMonthlyPayment();
		account.addToBalance(mounthlyAmount * subtractAmount);
		bankManager.account.addToBalance(mounthlyAmount);

		account.setLoanLeftMonths(account.getLoanLeftMonths() - 1);
	}

	protected void loan() {
		int loan = getDesiredLoan();
		if (loan == 0)
			return;

		int numOfMonths = getNumOfDesiredMonthsLoan();
		if (numOfMonths == 0)
			return;

		double mounthlyAmount = account.calcMonthlyPaymentForLoan(loan, numOfMonths);
		account.setLoanMonthlyPayment(mounthlyAmount);
		account.setLoanLeftMonths(numOfMonths);
		account.addToBalance(loan);
		bankManager.account.addToBalance(loan * subtractAmount);

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

	protected void logout() {
		System.out.println("GoodBye!");
	}

}
