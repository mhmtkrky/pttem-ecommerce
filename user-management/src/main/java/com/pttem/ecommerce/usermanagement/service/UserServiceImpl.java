package com.pttem.ecommerce.usermanagement.service;

import com.pttem.ecommerce.usermanagement.entity.UserEntity;
import com.pttem.ecommerce.usermanagement.repository.UserRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserEntity login(UserEntity model) {
        UserEntity userEntity = repository.findByEmailAndPassword(model.getEmail(),model.getPassword())
               .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        return userEntity;
    }

    @Override
    public UserEntity register(UserEntity model) {
        return repository.save(model);
    }

    @Override
    public UserEntity active(Long id) {
        UserEntity userEntity = repository.findById(id)
                .map(x -> {
                    x.setActive(true);
                    return x;
                }).orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        return repository.save(userEntity);
    }
}