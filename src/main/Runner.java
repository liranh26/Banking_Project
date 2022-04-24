package main;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AppManager app = new AppManager();
		boolean shutDown=false;
		
		System.out.println("Welcome to AJBC Bank!");
		while(!shutDown) {
			app.printLoginMenu();
			int option = sc.nextInt();
			sc.nextLine();
			switch(option) {
			case 1:
//				app.login();
				break;
			case 2:
				app.openAccount(sc);
				break;
			}
		}
		
		sc.close();
		
		int input = 1;
		System.out.println(input++);
		System.out.println(input);
		

	}

}
