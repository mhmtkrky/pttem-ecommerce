package com.pttem.ecommerce.categorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableSwagger2
@EnableDiscoveryClient
public class CategoryManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryManagementApplication.class, args);
    }
}
