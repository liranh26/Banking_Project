package accounts;

import java.time.LocalDate;

public class BankManager extends AccountOwner {
	protected AccountOwner[] usersToApprove;
	protected int approvalIndex;

	public BankManager() {
		this("Liran", "Hadad", "0545332205", LocalDate.of(1990, 2, 26), 20000, "manager", "aA1234");
	}

	public BankManager(String firstName, String lastName, String phone, LocalDate birthdate, double income,
			String userName, String password) {
		super(firstName, lastName, phone, birthdate, income, userName, password);
		
		approvalIndex = 0;
	}

	public void addUserToApprove(AccountOwner newUser) {
		usersToApprove[approvalIndex++] = newUser;
	}

	protected void setAndApproveAccount() {
		AccountProperties accountProperties;
		for (int i = 0; i < approvalIndex; i++) {
			//sets AccountType according to user income
			accountProperties = AccountProperties.getAccountType(usersToApprove[approvalIndex].getMounthlyIncome());
			usersToApprove[approvalIndex].account = new Account(accountProperties, 0);
		}
	}

}
