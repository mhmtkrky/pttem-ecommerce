package com.pttem.ecommerce.categorymanagement.service;

import com.pttem.ecommerce.categorymanagement.entity.CategoryEntity;
import org.springframework.validation.annotation.Validated;
import service.BaseService;

@Validated
public interface CategoryService extends BaseService<CategoryEntity> {
}
