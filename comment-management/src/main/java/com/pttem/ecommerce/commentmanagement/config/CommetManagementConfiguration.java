package com.pttem.ecommerce.commentmanagement.config;

import filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class CommetManagementConfiguration {
    private static final String BASE_PACKAGE = "com.pttem.ecommerce.commentmanagement";
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";
    private static final String CONTROLLER_PATH_REGEX = "/rest.*";

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();
        authFilter.setHandlerMappings(null);
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
