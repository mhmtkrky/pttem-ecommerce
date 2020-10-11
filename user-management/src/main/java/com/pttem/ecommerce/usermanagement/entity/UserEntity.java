package com.pttem.ecommerce.usermanagement.entity;

import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tbluser")
public class UserEntity extends AuditEntity {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

    @Column(name = "isActive")
    private Boolean active = false;
}
