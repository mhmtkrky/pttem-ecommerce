package com.pttem.ecommerce.purchasingmanagement.entity;

import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tblPurchasing")
public class PurchasingEntity extends AuditEntity {

    @Column(name = "totalPrice", nullable = false)
    private BigDecimal totalPrice = new BigDecimal(0);

    @Column(name = "userUUID", nullable = false)
    private Long userUUID = 0L;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @OneToMany(mappedBy="purchasing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotEmpty
    private Set<PurchasingDetailEntity> detailList = new HashSet<>();

    public void addDetail(PurchasingDetailEntity detail){
        this.detailList.add(detail);
        detail.setPurchasing(this);
    }
}
