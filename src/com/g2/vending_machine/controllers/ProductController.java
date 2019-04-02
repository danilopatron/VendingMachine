package com.g2.vending_machine.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
		boolean keepBuyig = false;
		
		//States
		boolean addMoreProducts = true;
		boolean addMoreMoney = true;
		
		List<Product> products = new ArrayList<Product>();
		Product product = null;
		String message = "";
		float amount = 0f;
		
		do {
			if(addMoreMoney) amount = this.handlePay(amount);
			if(amount == 0) break;
			
			if(addMoreProducts) {
				product = this.handleSelection(amount);
				if(product != null) products.add(product);
			}
			
			if(products.size() == 0) break;
			
			float change = amount - product.getProductPrice();
			if(change > 0) {
				addMoreProducts = this.keepProducts(change);
				addMoreMoney = false;
				keepBuyig = addMoreProducts;
				amount = change;
			} else if(change < 0) {
				addMoreProducts = true;
				addMoreMoney = true;
			} else {
				keepBuyig = false;
			}
			
			String productList = products.stream().map(Product::getProductName).collect(Collectors.joining(", "));
			message = String.format("Change: %.2f and Product(s): %s", change, productList);
			
		} while(keepBuyig);
		
		return message;
	}
	
	private Product handleSelection(float amount) {
		this.showProductList();
		
		Scanner scan = new Scanner(System.in);
		String code = null;
		do {
			System.out.printf("\nYou have %.2f. Introduce the code of the product: ", amount);
			code = scan.nextLine();
		
			if(!this.productList.containsKey(code)) {
				System.out.print("The selected code does not exist, please, try again.");
				code = null;
				continue;
			}
		} while(code == null);
		
		return this.productList.get(code);
	}
	
	private boolean keepProducts(float change) {
		System.out.printf("Your change is %.2f. Would you like to purchase anything else (Y/N)?: ", change);
		Scanner scan = new Scanner(System.in);
		String response = scan.nextLine();
		
		return response.trim().toUpperCase().equals("Y");
	}
	
	private float handlePay(float amount) {
		float accum = amount;
		int i;

		this.denominationMenu(amount);
		Scanner scan = new Scanner(System.in);
		do {
			i = scan.nextInt();
			
			if(i > 0 && i < 8) {
				accum += denomination[i-1];
				System.out.printf("You have $%.2f, enter more money (press 8 to SELECT PRODUCT): ", accum);
			} else if(i == 8) {
				// Do nothing yet
			} else {
				System.out.print("Please, introduce a correct denomination (press 8 to SELECT PRODUCT): ");
			}
			
		} while(i != 8);
		
		return accum;
	}
	
	private void denominationMenu(float amount) {
		ScreenUtils.clearScreen();
		System.out.println("\n1. 1 cent");
		System.out.println("2. 5 cent");
		System.out.println("3. 10 cent");
		System.out.println("4. 25 cent");
		System.out.println("5. 50 cent");
		System.out.println("6. 1 dollar");
		System.out.println("7. 2 dollar");
		String message = amount > 0f? "\nYou have $%.2f\n" : "\n";
		System.out.printf(message, amount);
		System.out.print("Based on above denomination list, introduce money (press 8 to SELECT PRODUCT): ");
	}

	private void showProductList() {
		ScreenUtils.clearScreen();
		System.out.println("\n");
		productList.entrySet()
					.stream()
					.forEach(e -> 
								System.out.println(
											e.getKey() + " - " +
											e.getValue().getProductName() + " $" +
											e.getValue().getProductPrice()));
		System.out.println("\n");
	}
}
