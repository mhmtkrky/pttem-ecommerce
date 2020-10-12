package com.pttem.ecommerce.purchasingmanagement.service;

import model.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ProductRestService {
    private final RestTemplate restTemplate;
    private final String PRODUCT_BASE_URL = "http://product-management/rest/product/";

    @Autowired
    public ProductRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "1");
        return headers;
    }

    private HttpEntity<Object> getHttpEntity() {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<Object>(headers);
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(body, headers);
    }

    public BigDecimal getUnitPriceForUUID(Long productUUID) {


        ResponseEntity<BigDecimal> unitPrice = restTemplate
                .exchange(PRODUCT_BASE_URL + "unitPrice/"
                        + productUUID, HttpMethod.GET, getHttpEntity(), BigDecimal.class);
        return unitPrice.getBody();
    }

    public Boolean controlStock(Long productUUID, Integer count) {

        return restTemplate
                .exchange(PRODUCT_BASE_URL + "unitInStock/"
                        , HttpMethod.POST, getHttpEntity(new ProductStock(productUUID, count))
                        , Boolean.class).getBody();
    }

    public Integer reduceStock(Long productUUID, Integer count) {
        return restTemplate
                .exchange(PRODUCT_BASE_URL + "reduceStock/"
                        , HttpMethod.POST, getHttpEntity(new ProductStock(productUUID, count))
                        , Integer.class).getBody();
    }
}
