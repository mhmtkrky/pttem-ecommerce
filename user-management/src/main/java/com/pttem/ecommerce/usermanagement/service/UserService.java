package com.pttem.ecommerce.usermanagement.service;

import com.pttem.ecommerce.usermanagement.entity.UserEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    UserEntity login(UserEntity model);
    UserEntity register(UserEntity model);
    UserEntity active(Long id);
}