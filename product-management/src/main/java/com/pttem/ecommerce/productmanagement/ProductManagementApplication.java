package com.pttem.ecommerce.productmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import service.BeanConfigurater;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
@EnableSwagger2
public class ProductManagementApplication {

    public static void main(String[] args) {

        BeanConfigurater.init(SpringApplication.run(ProductManagementApplication.class, args));
    }

}
