package com.pttem.ecommerce.commentmanagement.service;

import com.pttem.ecommerce.commentmanagement.entity.CommentEntity;
import org.springframework.validation.annotation.Validated;
import service.BaseService;

@Validated
public interface CommentService extends BaseService<CommentEntity> { }
