package accounts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import utils.Menus;
import utils.ScannerInputs;

public class BankManager extends AccountOwner {
	protected AccountOwner[] usersToApprove;
	private static int approvalIndex;

	public BankManager() {
		this("Liran", "Hadad", "0545332205", LocalDate.of(1990, 2, 26), 20000, "manager", "aA1234");
	}

	public BankManager(String firstName, String lastName, String phone, LocalDate birthdate, double income,
			String userName, String password) {
		super(firstName, lastName, phone, birthdate, income, userName, password);
		usersToApprove = new AccountOwner[100];
		approvalIndex = 0;
		setAccount(new Account(AccountProperties.TITANIUM, 0));
		account.setBalance(1000000);
	}

	public void addUserToApprove(AccountOwner newUser) {
		usersToApprove[approvalIndex++] = newUser;
	}

	protected void setAndApproveAccount() {
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

	@Override
	public void actionMenu() {
		int option = 0;
		System.out.println("Welcome " + this.getFirstName() + " what would you like to do?");
		while (option != 9) {
			Menus.managerActionMenu();
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
				setAndApproveAccount();
				break;
			case 9:
				logout();
				break;
			default:
				Menus.defaultMessage();
			}
		}
	}

	@Override
	protected void produceReport() {
		if (account.activityLog[0] == null) {
			System.out.println("No activity to present.");
			return;
		}

		LocalDateTime startDate = getDateStratForReport();
		for (int i = 0; i < account.getLogIndex(); i++) {
			long diff = ChronoUnit.HOURS.between(startDate, account.activityLog[i].getTimeStamp());
			if (diff > 0)
				Menus.printBankActivity(account.getBalance(),account.activityLog[i]);
		}
	}
	
	
}
