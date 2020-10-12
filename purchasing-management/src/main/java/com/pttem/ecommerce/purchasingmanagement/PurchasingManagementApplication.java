package com.pttem.ecommerce.purchasingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableJpaAuditing
public class PurchasingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchasingManagementApplication.class, args);
    }

}
