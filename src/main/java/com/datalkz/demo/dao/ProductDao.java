package com.datalkz.demo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.datalkz.demo.audit.annotation.AuditableService;
import com.datalkz.demo.bean.Product;

@AuditableService(module="PRODUCT")
@Repository
public class ProductDao {
	
	private static Map<String, Product> productMap = new HashMap<String, Product>();

	public Product viewProduct(Product product) {
		return productMap.get(product.getId());
		
	}


	public String addProduct(Product product) {
		productMap.put(product.getId(), product);
		return "Success";
	}

}
