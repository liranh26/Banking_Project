package tests;

import java.time.LocalDate;

import accounts.AccountOwner;

public class TestsObjects {
	
	AccountOwner user1 = new AccountOwner("aaa", "had", "12", LocalDate.of(1990, 2, 26), 12000, "aaa", "lir1");
	AccountOwner user2 = new AccountOwner("bbb", "H", "123", LocalDate.of(1990, 2, 26), 40000, "bbb", "lir1");
	AccountOwner user3 = new AccountOwner("ccc", "H", "1234", LocalDate.of(1990, 2, 26), 5000, "ccc", "lir1");


	public AccountOwner[] demoAccounts() {
		AccountOwner[] acc = new AccountOwner[3];
		acc[0] = user1;
		acc[1] = user2;
		acc[2] = user3;
		return acc;
	}
}
