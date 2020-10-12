package com.pttem.ecommerce.productmanagement.service;

import com.pttem.ecommerce.productmanagement.entity.ProductEntity;
import com.pttem.ecommerce.productmanagement.repository.ProductRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static exception.ResourceNotFoundException.getNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<ProductEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public BigDecimal getUnitPriceForUUID(Long uuid) throws ResourceNotFoundException {
        return repository.findById(uuid)
                .map(ProductEntity::getUnitPrice)
                .orElseThrow(getNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

    }

    @Override
    public Integer reduceStock(Long uuid, Integer count) throws ResourceNotFoundException {

        return repository.findById(uuid)
                .map(product -> repository.save(product.reduceUnitInStock(count)).getUnitInStock())
                .orElseThrow(getNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

    }

    @Override
    public Boolean controlStock(Long uuid, Integer count) {
        return repository.findById(uuid)
                .map(product -> product.getUnitInStock() >= count)
                .orElseThrow(getNotFoundException(PRODUCT_NOT_FOUND_MESSAGE));

    }
}
