package com.g2.vending_machine.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.g2.vending_machine.models.Product;

public class FileController {

	private static FileController fileController = null;
	private SortedMap<String, Product> productList = null;

	private FileController(String fileName) {
		productList = new TreeMap<String, Product>();
		try(BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
			String line;
			while((line = buffer.readLine()) != null) {
				String[] values = line.split(",");
				productList.put(values[1].trim(), new Product(values[0], Float.parseFloat(values[2])));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FileController getInstance(String fileName) {		
		if (fileController == null) {
			fileController = new FileController(fileName);
		}

		return fileController;
	}

	public Map<String, Product> getProductList() {
		return productList;
	}

}
