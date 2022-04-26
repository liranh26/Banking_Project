package main;

import utils.ScannerInputs;

public class Runner {
	
	public static void main(String[] args) {
		
		AppManager app = new AppManager();
		
		app.runApp();
		
		ScannerInputs.scanner.close();
		
	}

}
