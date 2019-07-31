package com.datalkz.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.datalkz.demo.bean.Product;
import com.datalkz.demo.bean.ResponseBean;
import com.datalkz.demo.facade.ProductFacade;
import com.datalkz.demo.security.utils.SecurityUtils;
import com.datalkz.demo.user.roles.UserRoles;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductFacade productFacade;
	
	@RequestMapping("/view/{id}")
	public ResponseBean viewProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String productId) {
		log.info("View Product Start. ProductId: {}", productId);
		return productFacade.viewProduct(request, productId);
		
	}
	
	@RequestMapping(method= {RequestMethod.POST}, value="/add")
	public ResponseBean addProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody() Product product) {
		log.info("Add Product Start. Product id : {}", product.getId());
		boolean canUserAccess = SecurityUtils.checkUserRole(UserRoles.ADMIN);
		if (!canUserAccess) {
			log.error("User is not authorized to access the particular resource");
			 ResponseBean responseBean = SecurityUtils.setUnAuthorizedStatus(response);
			 
			 return responseBean;
		}
		
		return productFacade.addProduct(request, product);
		
	}
}
