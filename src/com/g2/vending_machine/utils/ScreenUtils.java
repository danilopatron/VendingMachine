package com.g2.vending_machine.utils;

public class ScreenUtils {

	public ScreenUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	    System.out.println("Vending Machine");
	} 

}
