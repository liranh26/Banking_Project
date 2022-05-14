package banking_project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import accounts.Account;
import accounts.AccountProperties;

class AccuontTest {

	Account bronzeAcc ;
	Account silverAcc ;
	Account goldAcc ;
	Account titaniumAcc ;
	
	AccuontTest(){
		bronzeAcc = new Account(AccountProperties.BRONZE, 15.50);
		silverAcc = new Account(AccountProperties.SILVER, 1000.5);
		goldAcc = new Account(AccountProperties.GOLD, 2000.1);
		titaniumAcc = new Account(AccountProperties.TITANIUM, 15.50);
	}
	
	
	@Test
	void constructor() {
		
	}
	
	@Test
	void balance() {
		assertEquals(15.50, bronzeAcc.getBalance());
		assertEquals(1000.5, silverAcc.getBalance());
		assertEquals(2000.1, goldAcc.getBalance());
		assertEquals(15.50, titaniumAcc.getBalance());
	}
	
	

}
