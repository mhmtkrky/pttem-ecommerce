package com.pttem.ecommerce.purchasingmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tblPurchasingDetail")
public class PurchasingDetailEntity extends AuditEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchasing_id")
    private PurchasingEntity purchasing;

    @Column(name = "productUUID", nullable = false)
    private Long productUUID;

    @Column(name = "unitPrice", nullable = false)
    private BigDecimal unitPrice;

    @Min(1)
    @Column(name = "count", nullable = false)
    private Integer count;
}
