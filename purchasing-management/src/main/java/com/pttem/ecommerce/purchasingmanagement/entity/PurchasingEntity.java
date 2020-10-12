package com.pttem.ecommerce.purchasingmanagement.entity;

import entity.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblPurchasing")
public class PurchasingEntity extends AuditEntity {

    @Column(name = "totalPrice", nullable = false)
    private BigDecimal totalPrice = new BigDecimal(0);

    @Column(name = "userUUID", nullable = false)
    private Long userUUID = 0L;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @OneToMany(mappedBy = "purchasing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotEmpty
    private Set<PurchasingDetailEntity> detailList = new HashSet<>();

    public PurchasingDetailEntity addDetail(PurchasingDetailEntity detail) {
        this.detailList.add(detail);
        detail.setPurchasing(this);
        this.addTotalPrice(detail.getUnitPrice());
        return detail;
    }

    public void addTotalPrice(BigDecimal unitPrice) {
        setTotalPrice(getTotalPrice().add(unitPrice));
    }

    public PurchasingEntity subtractTotalPrice(BigDecimal unitPrice) {
        setTotalPrice(getTotalPrice().subtract(unitPrice));
        return this;
    }

    public PurchasingEntity incrementCountOrCreate(Long productUUID, BigDecimal unitPrice) {
        getDetailList()
                .stream()
                .filter(x -> x.getProductUUID().equals(productUUID))
                .findFirst()
                .map(PurchasingDetailEntity::incrementCount).orElseGet(() -> this.addDetail(
                PurchasingDetailEntity.builder()
                        .count(1)
                        .productUUID(productUUID)
                        .purchasing(this)
                        .unitPrice(unitPrice).build()));
        return this;
    }

}
