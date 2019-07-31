package com.datalkz.demo.facade;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datalkz.demo.bean.Product;
import com.datalkz.demo.bean.ResponseBean;
import com.datalkz.demo.service.ProductService;

@Service
public class ProductFacade {
	
	@Autowired
	ProductService productService;

	public ResponseBean viewProduct(HttpServletRequest request, String productId) {
		ResponseBean response = new ResponseBean();
		response.setData(this.productService.viewProduct(new Product(productId)));
		return response;
	}

	public ResponseBean addProduct(HttpServletRequest request, Product product) {
		ResponseBean response = new ResponseBean();
		response.setMessage(this.productService.addProduct(product));
		return response;
	}
	
	
	
	

}
