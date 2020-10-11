package com.pttem.ecommerce.productmanagement.entity;

import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
}