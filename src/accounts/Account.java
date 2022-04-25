package accounts;

public class Account {
	protected double balance;
	protected AccountProperties accountProperties;
	private static int idCounter=1;
	private final int ACCOUNT_ID;
	
	public Account(AccountProperties accountProperties, double balance) {
		this.accountProperties = accountProperties;
		setBalance(balance);
		ACCOUNT_ID = idCounter++;
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
	
	

}
