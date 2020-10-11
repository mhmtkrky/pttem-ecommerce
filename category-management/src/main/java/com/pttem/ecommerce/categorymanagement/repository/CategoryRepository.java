package com.pttem.ecommerce.categorymanagement.repository;

import com.pttem.ecommerce.categorymanagement.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
