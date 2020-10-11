package com.pttem.ecommerce.productmanagement.config;

import filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class ProductManagementConfiguration {
    private static final String BASE_PACKAGE = "com.pttem.ecommerce.productmanagement";
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";
    private static final String CONTROLLER_PATH_REGEX = "/rest.*";

    @Autowired
    private List<HandlerMapping> handlerMappings;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();
        authFilter.setHandlerMappings(handlerMappings);
        registrationBean.setFilter(authFilter);
        return registrationBean;

    }

    @Bean
    public Docket productApi() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_PACKAGE))
                .paths(PathSelectors.regex(CONTROLLER_PATH_REGEX))
                .build();
    }
}
