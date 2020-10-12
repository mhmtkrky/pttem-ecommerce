package com.pttem.ecommerce.productmanagement.service;

import com.pttem.ecommerce.productmanagement.entity.ProductEntity;
import org.springframework.validation.annotation.Validated;
import service.BaseService;

import java.math.BigDecimal;

@Validated
public interface ProductService extends BaseService<ProductEntity> {
    BigDecimal getUnitPriceForUUID(Long uuid);

    Integer reduceStock(Long uuid, Integer count);

    Boolean controlStock(Long uuid, Integer count);
}
