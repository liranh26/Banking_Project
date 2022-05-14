package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import activity.ActivityData;
import activity.ActivityName;
import utils.Menus;
import utils.ScannerInputs;

public class BankManager extends AccountOwner {
	protected AccountOwner[] usersToApprove;
	private static int approvalIndex;
	protected final int subtractAmount = -1;

	/**
	 * This constructor sets a default manager.
	 */
	public BankManager() {
		this("Liran", "Hadad", "0545332205", LocalDate.of(1990, 2, 26), 20000, "manager", "aA1234");
	}

	/**
	 * This constructor sets the bank manager which contains the bank account, it
	 * uses previous constructors. 
	 * It adds accountOwner array usersToApprove to the add the users which need manager approval.
	 *   
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
	public BankManager(String firstName, String lastName, String phone, LocalDate birthdate, double income,
			String userName, String password) {
		super(firstName, lastName, phone, birthdate, income, userName, password);
		usersToApprove = new AccountOwner[100];
		approvalIndex = 0;
		setAccount(new Account(AccountProperties.TITANIUM, 0));
		account.setBalance(1000000);
	}

	/**
	 * This method adds an account owner to the manager approval  
	 * @param newUser new account owner waiting for manager approve.
	 */
	public void addUserToApprove(AccountOwner newUser) {
		usersToApprove[approvalIndex++] = newUser;
	}

	/**
	 * This method set and approves account according to its income.
	 */
	public void setAndApproveAccount() {
		AccountProperties accountProperties;
		for (int i = 0; i < approvalIndex; i++) {
			// sets AccountType according to user income
			accountProperties = AccountProperties.getAccountType(usersToApprove[i].getMounthlyIncome());
			usersToApprove[i].account = new Account(accountProperties, 0);
			usersToApprove[i].account.setFeeOperation(accountProperties.setFeeOperation());
			usersToApprove[i].account.setInterstRate(accountProperties.setInterstRate());
			System.out.println("New account added!");
			usersToApprove[i].setBankManager(this);
		}
		approvalIndex = 0;
	}

	protected void collectFee(double fee) {
		account.addToBalance(fee);
	}

//	/**
//	 * This method over ride the original method in account owner to add users
//	 * approval action.
//	 */
//	@Override
//	public void actionMenu() {
//		int option = 0;
//		System.out.println("Welcome " + this.getFirstName() + " what would you like to do?");
//		while (option != 8) {
//			Menus.managerActionMenu();
//			option = ScannerInputs.scanner.nextInt();
//			ScannerInputs.scanner.nextLine();
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
//				setAndApproveAccount();
//				break;
//			case 8:
//				logout();
//				break;
//			default:
//				Menus.defaultMessage();
//			}
//		}
//	}

	/**
	 * This method over ride the account owner method for producing a custom report.
	 * It prints the bank balance and the changes in the balance account.
	 */
	@Override
	public void produceReport() {
		if (account.activityLog[0] == null) {
			System.out.println("No activity to present.");
			return;
		}

		LocalDateTime startDate = getDateStratForReport();
		for (int i = 0; i < account.getLogIndex(); i++) {
			long diff = ChronoUnit.HOURS.between(startDate, account.activityLog[i].getTimeStamp());
			if (diff > 0)
				Menus.printBankActivity(account.getBalance(), account.activityLog[i]);
		}
	}
	
	/**
	 * overrides the parent method to avoid exceptions when loging for the bank activity
	 */
	@Override
	public void deposit() {
		int authCode = ScannerInputs.getAuthNum();
		Menus.authCodeMessage(authCode);
		int input = ScannerInputs.getIntFromUser();
		if (input == authCode) {
			System.out.println("Insert deposit amount:");
			double depositAmount = ScannerInputs.getDoubleFromUser();
			account.addToBalance(depositAmount);
			account.setActivity(new ActivityData(ActivityName.DEPOSIT, account.getBalance(), LocalDateTime.now()));
			Menus.depositSuccess();
		} else
			Menus.depositFail();
	}
	
	/**
	 * overrides the parent method to avoid exceptions when loging for the bank activity
	 */
	@Override
	public void withdrawal() {
		int withdrawal = withdrawalAmount();
		if (withdrawal == 0)
			return;

		account.addToBalance(withdrawal * subtractAmount);
		account.setActivity(new ActivityData(ActivityName.WITHDRAWL, account.getBalance(), LocalDateTime.now()));
	}
	
	/**
	 * overrides the parent method to avoid exceptions when loging for the bank activity
	 */
	@Override
	public void transfer() {
		int transAmount = transferAmount();
		if (transAmount == 0)
			return;
		AccountOwner userRecieveTrans = phoneNumToTransfer();
		if (userRecieveTrans == null)
			return;
		userRecieveTrans.account.addToBalance(transAmount);
		account.addToBalance(transAmount * subtractAmount);
		System.out.println("Transfer Succeeded!");

		account.setActivity(new ActivityData(ActivityName.TRANSFER, account.getBalance(), LocalDateTime.now()));
	}
	
	/**
	 * overrides the parent method to avoid exceptions when loging for the bank activity
	 */
	@Override
	protected void pay() {
		int bill = billAmount();
		if (bill == 0)
			return;

		account.addToBalance(bill * subtractAmount);
		System.out.println("Bill payed successfuly!");
		account.setActivity(new ActivityData(ActivityName.PAY_BILL, account.getBalance(), LocalDateTime.now()));
	}
	
	
}
