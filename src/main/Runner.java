package main;

import java.util.Scanner;

public class Runner {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		AppManager app = new AppManager();
		boolean shutDown=false;
		
		System.out.println("Welcome to AJBC Bank!");
		while(!shutDown) {
			app.printLoginMenu();
			int option = scanner.nextInt();
			scanner.nextLine();
			switch(option) {
			case 1:
//				app.login();
				break;
			case 2:
				app.openAccount();
				break;
			}
		}
		
		scanner.close();
		
	}

}
