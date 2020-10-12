package com.pttem.ecommerce.usermanagement.controller;

import com.pttem.ecommerce.usermanagement.entity.UserEntity;
import com.pttem.ecommerce.usermanagement.service.UserService;
import filter.AdminLevelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user/login")
    private UserEntity login(@RequestBody UserEntity model) {
        return service.login(model);
    }

    @PostMapping("/user/register")
    private UserEntity register(@RequestBody UserEntity model) {
        return service.register(model);
    }

    @PostMapping("/user/active/{id}")
    @AdminLevelRequest
    private UserEntity active(@PathVariable Long id) {
        return service.active(id);
    }
}
