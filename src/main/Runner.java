package main;

import java.util.Scanner;

public class Runner {
	public static Scanner scanner = new Scanner(System.in);

	
	public static void main(String[] args) {
		AppManager app = new AppManager();
		
		app.runApp();
		
		scanner.close();
		
	}

}
