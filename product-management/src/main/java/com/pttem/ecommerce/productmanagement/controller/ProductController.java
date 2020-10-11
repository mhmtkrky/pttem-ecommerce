package com.pttem.ecommerce.productmanagement.controller;

import com.pttem.ecommerce.productmanagement.entity.ProductEntity;
import com.pttem.ecommerce.productmanagement.service.ProductService;
import controller.BaseController;
import exception.ResourceNotFoundException;
import filter.AdminLevelRequest;
import model.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BaseService;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/rest/product")
@RestController
public class ProductController implements BaseController<ProductEntity> {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @Override
    public BaseService<ProductEntity> getService() {
        return service;
    }

    @AdminLevelRequest
    @GetMapping("/unitPrice/{uuid}")
    public BigDecimal getUnitPriceForUUID(@PathVariable Long uuid) throws ResourceNotFoundException {
        return service.getUnitPriceForUUID(uuid);
    }

    @PostMapping("/reduceStock/")
    public Integer reduceStock(@RequestBody ProductStock model) throws ResourceNotFoundException {
        return service.reduceStock(model.getUuid(), model.getCount());
    }

    @PostMapping("/unitInStock/")
    public Boolean controlStock(@RequestBody ProductStock model) throws ResourceNotFoundException {
        return service.controlStock(model.getUuid(), model.getCount());
    }
}
