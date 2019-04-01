package com.g2.vending_machine.controllers;

import java.util.Map;
import java.util.Scanner;

import com.g2.vending_machine.models.Product;
import com.g2.vending_machine.utils.ScreenUtils;

public class ProductController {
	private static ProductController productController = null;
	private Map<String, Product> productList = null;
	private final float[] denomination = {0.01f, 0.05f, 0.1f, 0.25f, 0.5f, 1.0f, 2.0f};
	
	private ProductController(Map<String, Product> productList) {
		// TODO Auto-generated constructor stub
		this.productList = productList;
	}
	
	public static ProductController getInstance(Map<String, Product> productList) {
		if(productController == null) {
			productController = new ProductController(productList);
		}
		
		return productController;
	}
	
	public String handleProductTransaction() {
		boolean buy = true;
		String message = "";
		
		do {
			
			float amount = this.handlePay(0f);
			if(amount == 0) break;
			
			Product product = this.handleSelection();
			if(product == null) break;
			
			float change = amount - product.getProductPrice();
			if(change < 0) break;
			
			message = String.format("Change: %.2f and Product: %s", change, product.getProductName());
			buy = false;
			
		} while(buy);
		
		return message;
	}
	
	private Product handleSelection() {
		this.showProductList();
		
		Scanner scan = new Scanner(System.in);
		String code = null;
		do {
			System.out.print("Introduce the code of the product: ");
			code = scan.nextLine();
		
			if(!this.productList.containsKey(code)) {
				System.out.print("The selected code does not exist, please, try again.");
				code = null;
				continue;
			}
		} while(code == null);
		
		return this.productList.get(code);
	}
	
	private float handlePay(float amount) {
		float accum = amount;
		int i;

		this.denominationMenu();
		Scanner scan = new Scanner(System.in);
		do {
			i = scan.nextInt();
			
			if(i > 0 && i < 8) {
				accum += denomination[i-1];
				System.out.printf("You have $%.2f, enter more money (press 8 to SELECT PRODUCT): ", accum);
			} else if(i == 8) {
				// Do nothing yet
			} else {
				System.out.println("Please, introduce a correct denomination (press 8 to SELECT PRODUCT): ");
			}
			
		} while(i != 8);
		
		return accum;
	}
	
	private void denominationMenu() {
		ScreenUtils.clearScreen();
		System.out.println("1. 1 cent");
		System.out.println("2. 5 cent");
		System.out.println("3. 10 cent");
		System.out.println("4. 25 cent");
		System.out.println("5. 50 cent");
		System.out.println("6. 1 dollar");
		System.out.println("7. 2 dollar");
		System.out.println();
		System.out.print("Based on above denomination list, introduce money (press 8 to SELECT PRODUCT): ");
	}

	private void showProductList() {
		ScreenUtils.clearScreen();
		productList.entrySet()
					.stream()
					.forEach(e -> 
								System.out.println(
											e.getKey() + " - " +
											e.getValue().getProductName() + " $" +
											e.getValue().getProductPrice()));
	}
}
