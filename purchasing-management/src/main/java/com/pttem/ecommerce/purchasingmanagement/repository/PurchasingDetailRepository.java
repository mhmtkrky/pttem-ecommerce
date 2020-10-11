package com.pttem.ecommerce.purchasingmanagement.repository;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasingDetailRepository extends JpaRepository<PurchasingDetailEntity, Long> {
}
