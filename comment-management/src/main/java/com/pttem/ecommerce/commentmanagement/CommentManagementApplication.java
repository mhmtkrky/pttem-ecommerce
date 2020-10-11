package com.pttem.ecommerce.commentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class CommentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentManagementApplication.class, args);
	}

}
