package com.g2.vending_machine.controllers;

import java.util.Map;
import java.util.Scanner;

import com.g2.vending_machine.models.Product;

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
	
	public void showProductList() {
		productList.entrySet()
					.stream()
					.forEach(e -> 
								System.out.println(
											e.getKey() + " - " +
											e.getValue().getProductName() + " $" +
											e.getValue().getProductPrice()));
	}
	
	public void showProductOptions() {
		String code = handleSelection();
		Product product = this.productList.get(code);
		handlePay(product.getProductPrice());
	}
	
	private String handleSelection() {
		System.out.println("Introduce the code of the product: ");
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}
	
	private float handlePay(float price) {
		float accum = 0;
		int i;
		
		denominationMenu();
		Scanner scan = new Scanner(System.in);
		do {
			i = scan.nextInt();
			
			if(i > 0 && i < 8) {
				accum += denomination[i-1];
				if(price - accum > 0) {
					System.out.printf("You need %.2f to complete the pay", (price - accum));
				}
			} else {
				System.out.println("Please, introduce a correct denomination");
			}
			
		} while(price > accum);
		
		return 0;
	}
	
	private void denominationMenu() {
		System.out.println("Select currency denomination(Introduce a combination of denimination until you cover the pay):");
		System.out.println("1. 1 cent");
		System.out.println("2. 5 cent");
		System.out.println("3. 10 cent");
		System.out.println("4. 25 cent");
		System.out.println("5. 50 cent");
		System.out.println("6. 1 dollar");
		System.out.println("7. 2 dollar");
	}
}
