package com.pttem.ecommerce.productmanagement.service;

import com.pttem.ecommerce.productmanagement.entity.ProductEntity;
import com.pttem.ecommerce.productmanagement.repository.ProductRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

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
                .orElseThrow(()
                        -> new ResourceNotFoundException("Product not found"));

    }

    @Override
    public Integer reduceStock(Long uuid, Integer count) throws ResourceNotFoundException {
        ProductEntity product = repository.findById(uuid)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Product not found"));

        if (product.getUnitInStock() - count < 0) {
            throw new ResourceNotFoundException("Stock quantity could be not negative");
        } else {
            product.setUnitInStock(product.getUnitInStock() - count);
            repository.save(product);
            return product.getUnitInStock();
        }
    }

    @Override
    public Boolean controlStock(Long uuid, Integer count) {
        ProductEntity product = repository.findById(uuid)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Product not found"));
        return product.getUnitInStock() >= count;
    }
}
