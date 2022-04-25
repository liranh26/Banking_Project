package main;

import utils.ScannerInputs;

public class Runner {
	
	public static void main(String[] args) {
		AppManager app = new AppManager();
		
		app.runApp();
		
		ScannerInputs.scanner.close();


//		long diff = ChronoUnit.HOURS.between( LocalDateTime.of(2022, 4, 25, 9, 0), LocalDateTime.now());
//		System.out.println(diff);
	}

}
