package accounts;

public class Account {
	protected double balance;
	protected AccountProperties accountProperties;

	public Account(AccountProperties accountProperties, double balance) {
		this.accountProperties = accountProperties;
		setBalance(balance);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
