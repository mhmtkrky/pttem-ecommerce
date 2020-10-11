package com.pttem.ecommerce.commentmanagement.repository;

import com.pttem.ecommerce.commentmanagement.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
