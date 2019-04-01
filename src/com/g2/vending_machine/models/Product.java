package com.g2.vending_machine.models;

public class Product {

	private String productName;
	private float productPirce;
	
	public Product(String productName, float productPrice) {
		this.productName = productName;
		this.productPirce = productPrice;
	}

	public String getProductName() {
		return productName;
	}

	public Product setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public float getProductPrice() {
		return productPirce;
	}

	public Product setProductPirce(float productPirce) {
		this.productPirce = productPirce;
		return this;
	}

}
