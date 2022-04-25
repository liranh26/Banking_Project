package utils;

import java.util.Scanner;

public class ScannerInputs {
	public static Scanner scanner = new Scanner(System.in);
	
	
	public static int getIntFromUser() {
		int num = ScannerInputs.scanner.nextInt();
		ScannerInputs.scanner.nextLine();
		return num;
	}
	
	public static double getDoubleFromUser() {
		double num = ScannerInputs.scanner.nextDouble();
		ScannerInputs.scanner.nextLine();
		return num;
	}
	
}
