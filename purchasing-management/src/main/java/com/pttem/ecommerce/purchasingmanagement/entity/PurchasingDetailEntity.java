package com.pttem.ecommerce.purchasingmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public PurchasingDetailEntity incrementCount() {
        setCount(getCount() + 1);
        return this;
    }

    public PurchasingDetailEntity decrementCount() {
        setCount(getCount() - 1);
        return this;
    }
}
