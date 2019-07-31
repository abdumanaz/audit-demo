package com.datalkz.demo.bean;

import com.datalkz.demo.audit.annotation.AuditableBean;

@AuditableBean
public class Product {

	private String id;
	private String name;
	private String description;
	private String category;
	private float price;
	
	public Product() {
		
	}
	
	public Product(String id) {
		this.id = id;
	}
	
	public Product(String id, String name, String description, String category, float price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
