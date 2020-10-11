package com.pttem.ecommerce.purchasingmanagement.repository;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingRepository extends JpaRepository<PurchasingEntity, Long> {

    List<PurchasingEntity> findByUserUUID(Long userUUID);
}
