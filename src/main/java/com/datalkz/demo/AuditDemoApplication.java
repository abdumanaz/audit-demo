package com.datalkz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class AuditDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditDemoApplication.class, args);
	}

}
