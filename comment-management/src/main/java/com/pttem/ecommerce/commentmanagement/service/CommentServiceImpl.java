package com.pttem.ecommerce.commentmanagement.service;

import com.pttem.ecommerce.commentmanagement.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final JpaRepository<CommentEntity, Long> repository;

    @Autowired
    public CommentServiceImpl(JpaRepository<CommentEntity, Long> repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<CommentEntity, Long> getRepository() {
        return repository;
    }
}
