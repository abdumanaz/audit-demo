package com.datalkz.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datalkz.demo.audit.annotation.AuditableParam;
import com.datalkz.demo.bean.Product;
import com.datalkz.demo.dao.ProductDao;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	public Product viewProduct(Product product) {
		return this.productDao.viewProduct(product);
	}

	public String addProduct(Product product) {
		return this.productDao.addProduct(product);
	}

}
