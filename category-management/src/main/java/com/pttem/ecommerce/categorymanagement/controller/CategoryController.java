package com.pttem.ecommerce.categorymanagement.controller;

import com.pttem.ecommerce.categorymanagement.entity.CategoryEntity;
import com.pttem.ecommerce.categorymanagement.service.CategoryService;
import controller.BaseController;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BaseService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class CategoryController implements BaseController<CategoryEntity> {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Override
    public BaseService<CategoryEntity> getService() {
        return service;
    }

    @Override
    @GetMapping("/category")
    public List<CategoryEntity> getList() {
        return BaseController.super.getList();
    }

    @Override
    @PostMapping("/category")
    public CategoryEntity create(@RequestBody CategoryEntity model) {
        return BaseController.super.create(model);
    }

    @Override
    @PutMapping("/category")
    public CategoryEntity edit(@RequestBody CategoryEntity model) throws ResourceNotFoundException {
        return BaseController.super.edit(model);
    }

    @Override
    @GetMapping("/category/{id}")
    public CategoryEntity getById(@PathVariable Long id) throws ResourceNotFoundException {
        return BaseController.super.getById(id);
    }

    @Override
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        return BaseController.super.delete(id);
    }
}
