package com.pttem.ecommerce.purchasingmanagement.service;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingEntity;
import com.pttem.ecommerce.purchasingmanagement.model.AddProductToPurchasingModel;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface PurchasingService {
    PurchasingEntity addProduct(AddProductToPurchasingModel model);

    PurchasingEntity removeProduct(AddProductToPurchasingModel model);

    PurchasingEntity complete(Long id);

    List<PurchasingEntity> getList();

    List<PurchasingEntity> getUserUUID(Long uuid);
}
