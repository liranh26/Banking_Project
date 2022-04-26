package accounts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Account {
	protected double balance;
	protected AccountProperties accountProperties;
	private static int idCounter = 1;
	private final int ACCOUNT_ID;
	protected ActivityData[] activityLog;
	private int logIndex;
	private final int recordNumOfActivites = 100;
	protected double feeOperation;
	protected double interstRate;
	private LocalDateTime lastWithdrawal;
	private int dailyWithraw;
	protected double loanMonthlyPayment;
	protected int loanLeftMonths;
	protected LocalDateTime loginFailure;
	protected int loan;

	/**
	 * This consturctor takes an account properties and the initial balance of the
	 * account.
	 * 
	 * @param accountProperties represent data about the user for actions.
	 * @param balance           the balance of the account.
	 */
	public Account(AccountProperties accountProperties, double balance) {
		this.accountProperties = accountProperties;
		setBalance(balance);
		ACCOUNT_ID = idCounter++;
		activityLog = new ActivityData[recordNumOfActivites];
		logIndex = 0;
		dailyWithraw = accountProperties.maxWithdraw;
		lastWithdrawal = LocalDateTime.now();
		loanMonthlyPayment = 0;
		loanLeftMonths = 0;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	protected void addToBalance(double deposit) {
		setBalance(deposit + getBalance());
	}

	public void setFeeOperation(double feeOperation) {
		this.feeOperation = feeOperation;
	}

	public double getFeeOperation() {
		return feeOperation;
	}

	public void setInterstRate(double interstRate) {
		this.interstRate = interstRate;
	}

	/**
	 * This method logs the activity of a user.
	 * 
	 * @param activity is the action the user did.
	 */
	protected void setActivity(ActivityData activity) {
		activityLog[logIndex++] = activity;
	}

	public int getLogIndex() {
		return logIndex;
	}

	public double getInterstRate() {
		return interstRate;
	}

	public int getLoan() {
		return loan;
	}

	/**
	 * This method checks whether the amount submitted is allowed to withdrawal by
	 * the user. if the amount not exceed the dailt limit and not the account limit
	 * it return true.
	 * 
	 * @param the amount the check if is valid
	 * @return true if the amount is valid and false if not.
	 */
	protected boolean isWithdrawalAvailble(int amount) {
		long diff = ChronoUnit.HOURS.between(lastWithdrawal, LocalDateTime.now());
		if (amount > accountProperties.maxWithdraw)
			return false;
		// check if during the day the total amount withdrawal is over the limit
		if (diff < 24 && dailyWithraw < amount)
			return false;
		// if passed 24 hours the daily amount returns to max
		if (diff >= 24)
			dailyWithraw = accountProperties.maxWithdraw;
		// set the current time and daily amount withdrawal
		dailyWithraw -= amount;
		lastWithdrawal = LocalDateTime.now();

		return true;

	}

	protected double calcMonthlyPaymentForLoan(int loan, int numOfMonth) {
		this.loan = loan;
		double monthlyAmount = loan / numOfMonth;
		monthlyAmount += monthlyAmount * interstRate / 100;
		return monthlyAmount;
	}

	protected double getTotalDebt() {
		return loanMonthlyPayment * loanLeftMonths;
	}

	protected double getLoanMonthlyPayment() {
		return loanMonthlyPayment;
	}

	protected void setLoanMonthlyPayment(double loanMonthlyPayment) {
		this.loanMonthlyPayment = loanMonthlyPayment;
	}

	protected int getLoanLeftMonths() {
		return loanLeftMonths;
	}

	protected void setLoanLeftMonths(int loanLeftMonths) {
		this.loanLeftMonths = loanLeftMonths;
	}

	protected LocalDateTime getLoginFailure() {
		return loginFailure;
	}

	public void setLoginFailure(LocalDateTime loginFailure) {
		this.loginFailure = loginFailure;
	}

	/**
	 * 
	 * @return true if difference between now to that last login time the account
	 *         was locked return
	 */
	public boolean isAccountLocked() {
		// if the login wasn't initialized yet
		if (getLoginFailure() == null)
			return false;
		long diff = ChronoUnit.MINUTES.between(getLoginFailure(), LocalDateTime.now());
		if (diff < 30)
			return true;
		return false;
	}

}
