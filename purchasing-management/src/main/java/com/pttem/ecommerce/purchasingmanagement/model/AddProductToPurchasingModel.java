package com.pttem.ecommerce.purchasingmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddProductToPurchasingModel {
    private Long userUUID;
    private Long productUUID;
}