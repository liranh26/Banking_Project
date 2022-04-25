package accounts;

public class Account {
	protected double balance;
	protected AccountProperties accountProperties;
	private static int idCounter=1;
	private final int ACCOUNT_ID;
	protected ActivityData[] activityLog; //TODO complete!
	private static int logIndex;
	private final int recordNumOfActivites=100;
	protected double feeOperation;
	protected double interstRate;
	
	
	public Account(AccountProperties accountProperties, double balance) {
		this.accountProperties = accountProperties;
		setBalance(balance);
		ACCOUNT_ID = idCounter++;
		activityLog = new ActivityData[recordNumOfActivites];
		logIndex=0;
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
		setBalance(deposit+getBalance());
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
	

}
