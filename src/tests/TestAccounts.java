package tests;

import java.time.LocalDate;
import java.util.ArrayList;

import accounts.AccountOwner;

public class TestAccounts {
	
	AccountOwner user1 = new AccountOwner("aaa", "had", "12", LocalDate.of(1990, 2, 26), 12000, "aaa", "lir1");
	AccountOwner user2 = new AccountOwner("bbb", "H", "123", LocalDate.of(1990, 3, 30), 40000, "bbb", "lir1");
	AccountOwner user3 = new AccountOwner("ccc", "H", "1234", LocalDate.of(1990, 7, 8), 5000, "ccc", "lir1");


	public ArrayList<AccountOwner> demoAccounts() {
		ArrayList<AccountOwner> acc = new ArrayList<AccountOwner>();
		acc.add(user1);
		acc.add(user2);
		acc.add(user3);

		return acc;
	}
}
