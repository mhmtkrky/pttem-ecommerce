package com.pttem.ecommerce.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.UiConfiguration;

@Configuration
public class ApiGatewayConfig {

  @Bean
  UiConfiguration uiConfig() {
    return new UiConfiguration(
        "validatorUrl",
        "list",
        "alpha",
        "schema",
        UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
        false,
        true);
  }
}
