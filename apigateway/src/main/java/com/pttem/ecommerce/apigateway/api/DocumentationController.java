package com.pttem.ecommerce.apigateway.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Primary
@EnableAutoConfiguration
@EnableSwagger2
public class DocumentationController implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("user-management", "/api/user-management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("comment-management", "/api/comment-management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("product-management", "/api/product-management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("purchasing-management", "/api/purchasing-management/v2/api-docs", "2.0"));
        resources.add(swaggerResource("category-management", "/api/category-management/v2/api-docs", "2.0"));
        return resources;
    }



    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}