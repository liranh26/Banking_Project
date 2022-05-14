package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import activity.ActivityData;
import activity.ActivityName;
import utils.Menus;
import utils.ScannerInputs;

public class BankManager extends AccountOwner {
//	protected AccountOwner[] usersToApprove;
	protected ArrayList<AccountOwner> usersToApprove;
//	private static int approvalIndex;
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
		usersToApprove = new ArrayList<AccountOwner>();
//		approvalIndex = 0;
		setAccount(new Account(AccountProperties.TITANIUM, 0));
		account.setBalance(1000000);
	}

	/**
	 * This method adds an account owner to the manager approval  
	 * @param newUser new account owner waiting for manager approve.
	 */
	public void addUserToApprove(AccountOwner newUser) {
//		usersToApprove[approvalIndex++] = newUser;
		usersToApprove.add(newUser);
	}

	
	public void addUserToApprove( ArrayList<AccountOwner> newUser) {
		usersToApprove.addAll(newUser);
	}
	
	
	public void doForAcc(AccountOwner acc) {
		
	}
	
	/**
	 * This method set and approves account according to its income.
	 */
	public void setAndApproveAccount() {
		
		usersToApprove.stream().forEach(acc -> {
			AccountProperties tmpAccProperties = AccountProperties.getAccountType(acc.getMounthlyIncome());
			acc.account = new Account(tmpAccProperties, 0);
			acc.account.setFeeOperation(tmpAccProperties.setFeeOperation());
			acc.account.setInterstRate(tmpAccProperties.setInterstRate());
			System.out.println("New account added!");
			acc.setBankManager(this);
		}) ;
		usersToApprove.removeAll(usersToApprove);
//		AccountProperties tmpAccProperties;
//		for (int i = 0; i < usersToApprove.size(); i++) {
//			// sets AccountType according to user income
//			tmpAccProperties = AccountProperties.getAccountType(usersToApprove.get(i).getMounthlyIncome());
//			usersToApprove.get(i).account = new Account(tmpAccProperties, 0);
//			usersToApprove.get(i).account.setFeeOperation(tmpAccProperties.setFeeOperation());
//			usersToApprove.get(i).account.setInterstRate(tmpAccProperties.setInterstRate());
//			System.out.println("New account added!");
//			usersToApprove.get(i).setBankManager(this);
//		}
//		approvalIndex = 0;
	}

	protected void collectFee(double fee) {
		account.addToBalance(fee);
	}

	
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
