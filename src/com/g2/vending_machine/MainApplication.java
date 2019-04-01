package com.g2.vending_machine;

import java.util.Map;
import java.util.Scanner;

import com.g2.vending_machine.controllers.*;
import com.g2.vending_machine.models.Product;
import com.g2.vending_machine.utils.ScreenUtils;

public class MainApplication {

	public static void main(String[] args) {
		
		FileController fc = null;
		ProductController pc = null;
		Map<String, Product> productList = null;
		int option;
		String message = "";
		
		do {
			ScreenUtils.clearScreen();
			option = mainMenu(message);
			message = "";
			
			switch(option) {
				case 1:
					ScreenUtils.clearScreen();
					fc = FileController.getInstance(fileUploaded());
					productList = fc.getProductList();
					message = String.format("%d Product(s) successfully uploaded.", productList.size());
					break;
				case 2:
					ScreenUtils.clearScreen();
					pc = ProductController.getInstance(productList);
					message = pc.handleProductTransaction();
					break;
				default:
					ScreenUtils.clearScreen();
					message = String.format("It's not an option, please, try again.");
					break;
			}
			
		} while(option != 0);
	}
	
	private static int mainMenu(String message) {
		System.out.printf("%s\n\n", message);
		System.out.println("0 Exit");
		System.out.println("1 Upload product list");
		System.out.println("2 Buy a product");
		System.out.print("Option: ");
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	private static String fileUploaded() {
		System.out.print("Insert the path + file name: ");
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
}
