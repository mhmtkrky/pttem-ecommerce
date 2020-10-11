package com.pttem.ecommerce.commentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentManagementApplication.class, args);
	}

}
