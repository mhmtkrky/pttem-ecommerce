package com.pttem.ecommerce.purchasingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PurchasingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchasingManagementApplication.class, args);
	}

}
