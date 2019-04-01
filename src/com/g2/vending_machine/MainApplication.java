package com.g2.vending_machine;

import java.util.Map;
import java.util.Scanner;

import com.g2.vending_machine.controllers.*;
import com.g2.vending_machine.models.Product;

public class MainApplication {

	public static void main(String[] args) {
		
		FileController fc = null;
		ProductController pc = null;
		Map<String, Product> productList = null;
		int option;
		
		do {
			option = mainMenu();
			
			switch(option) {
				case 1:
					fc = FileController.getInstance(fileUploaded());
					productList = fc.getProductList();
					break;
				case 2:
					pc = ProductController.getInstance(productList);
					pc.showProductList();
					pc.showProductOptions();
					break;
				default:
					System.out.println("It's not an option, please, try again.");
					break;
			}
			
		} while(option != 0);
	}
	
	private static int mainMenu() {
		
		System.out.println("Vending Machine");
		System.out.println("0 Exit");
		System.out.println("1 Upload product list");
		System.out.println("2 Buy a product");

		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	private static String fileUploaded() {
		System.out.println("Insert the path + file name: ");
		
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
}
