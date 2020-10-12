package com.pttem.ecommerce.productmanagement.entity;

import entity.AuditEntity;
import exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tblproduct")
public class ProductEntity extends AuditEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Min(1)
    @Column(name = "unitPrice", nullable = false)
    private BigDecimal unitPrice;

    @Min(1)
    @Column(name = "unitInStock", nullable = false)
    private Integer unitInStock;

    @Column(name = "categoryUUID", nullable = false)
    private Long categoryUUID;

    public ProductEntity reduceUnitInStock(Integer count) {
        final String STOCK_QUANTITY_COULD_NOT_BE_NEGATIVE_MESSAGE = "Stock quantity could not be negative";
        if (getUnitInStock() < count) {
            throw new ResourceNotFoundException(STOCK_QUANTITY_COULD_NOT_BE_NEGATIVE_MESSAGE);
        }
        setUnitInStock(getUnitInStock() - count);
        return this;
    }
}