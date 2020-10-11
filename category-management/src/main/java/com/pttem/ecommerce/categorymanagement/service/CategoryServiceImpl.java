package com.pttem.ecommerce.categorymanagement.service;

import com.pttem.ecommerce.categorymanagement.entity.CategoryEntity;
import com.pttem.ecommerce.categorymanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<CategoryEntity, Long> getRepository() {
        return repository;
    }
}
