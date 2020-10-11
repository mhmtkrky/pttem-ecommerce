package com.pttem.ecommerce.commentmanagement.controller;

import com.pttem.ecommerce.commentmanagement.entity.CommentEntity;
import com.pttem.ecommerce.commentmanagement.service.CommentService;
import controller.BaseController;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BaseService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class CommentController implements BaseController<CommentEntity> {

    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @Override
    public BaseService<CommentEntity> getService() {
        return service;
    }

    @Override
    @GetMapping("/comment")
    public List<CommentEntity> getList() {
        return BaseController.super.getList();
    }

    @Override
    @PostMapping("/comment")
    public CommentEntity create(CommentEntity model) {
        return BaseController.super.create(model);
    }

    @Override
    @PutMapping("/comment")
    public CommentEntity edit(CommentEntity model) throws ResourceNotFoundException {
        return BaseController.super.edit(model);
    }

    @Override
    @GetMapping("/comment/{id}")
    public CommentEntity getById(Long id) throws ResourceNotFoundException {
        return BaseController.super.getById(id);
    }

    @Override
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> delete(Long id) throws ResourceNotFoundException {
        return BaseController.super.delete(id);
    }
}
