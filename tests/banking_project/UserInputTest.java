package banking_project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utils.ScannerInputs;

class UserInputTest {

	UserInputTest(){
		
	}
	
	@Test
	void getAuthNum() {
		int authNum = ScannerInputs.getAuthNum();
		assertTrue( authNum >= 1000 && authNum <=9999);
	}

	
}
