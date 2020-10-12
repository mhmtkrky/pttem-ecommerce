package com.pttem.ecommerce.usermanagement.service;

import com.pttem.ecommerce.usermanagement.entity.UserEntity;
import com.pttem.ecommerce.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static exception.ResourceNotFoundException.getNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final String RECORD_NOT_FOUND_MESSAGE = "Record not found";

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserEntity login(UserEntity model) {
        return repository.findByEmailAndPassword(model.getEmail(), model.getPassword())
                .orElseThrow(getNotFoundException(RECORD_NOT_FOUND_MESSAGE));
    }


    @Override
    public UserEntity register(UserEntity model) {
        return repository.save(model);
    }

    @Override
    public UserEntity active(Long id) {
        return repository.save(repository.findById(id)
                .map(UserEntity::activate)
                .orElseThrow(getNotFoundException(RECORD_NOT_FOUND_MESSAGE)));
    }
}