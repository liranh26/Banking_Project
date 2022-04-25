package accounts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Account {
	protected double balance;
	protected AccountProperties accountProperties;
	private static int idCounter = 1;
	private final int ACCOUNT_ID;
	protected ActivityData[] activityLog;
	private static int logIndex;
	private final int recordNumOfActivites = 100;
	protected double feeOperation;
	protected double interstRate;
	private static LocalDateTime lastWithdrawal;
	private static int dailyWithraw;
	protected double loanMonthlyPayment;
	protected int loanLeftMonths;
	
	public Account(AccountProperties accountProperties, double balance) {
		this.accountProperties = accountProperties;
		setBalance(balance);
		ACCOUNT_ID = idCounter++;
		activityLog = new ActivityData[recordNumOfActivites];
		logIndex = 0;
		dailyWithraw = accountProperties.maxWithdraw;
		lastWithdrawal = LocalDateTime.now();
		loanMonthlyPayment=0;
		loanLeftMonths=0;
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

	protected void setActivity(ActivityData activity) {
		activityLog[logIndex++] = activity;
	}

	public int getLogIndex() {
		return logIndex;
	}

	protected boolean isWithdrawalAvailble(int amount) {
		long diff = ChronoUnit.HOURS.between(lastWithdrawal, LocalDateTime.now());
		if (amount > accountProperties.maxWithdraw)
			return false;
		//check if during the day the total amount withdrawal is over the limit
		if (diff < 24 && dailyWithraw < amount)
			return false;
		//if passed 24 hours the daily amount returns to max
		if (diff >= 24)
			dailyWithraw = accountProperties.maxWithdraw;
		//set the current time and daily amount withdrawal
		dailyWithraw -= amount;
		lastWithdrawal = LocalDateTime.now();

		return true;

	}

	
	protected double calcMonthlyPaymentForLoan(int loan, int numOfMonth) {
		double mounthlyAmount = loan / numOfMonth;
		mounthlyAmount *=  (interstRate/100);
		return mounthlyAmount;
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


	
}
