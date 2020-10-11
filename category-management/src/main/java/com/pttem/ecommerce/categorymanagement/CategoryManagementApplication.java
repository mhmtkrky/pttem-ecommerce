package com.pttem.ecommerce.categorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CategoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryManagementApplication.class, args);
	}

}
