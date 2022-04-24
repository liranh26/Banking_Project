package main;

import java.util.Random;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		AppManager app = new AppManager();
//		boolean shutDown=false;
//		
//		System.out.println("Welcome to AJBC Bank!");
//		while(!shutDown) {
//			app.printRegisterMenu();
//			int option = sc.nextInt();
//			
//			switch(option) {
//			case 2:
//				app.openAccount(sc);
//				break;
//			}
//		}
//		
//		sc.close();
		
//		int input = 1;
//		System.out.println(input++);
//		System.out.println(input);
		
		Random random = new Random();
		double x =random.doubles(3.5, 6).findFirst().getAsDouble();

		double y = (Math.round(x*100.0)/100.0);
	    System.out.println(y);

	}

}
