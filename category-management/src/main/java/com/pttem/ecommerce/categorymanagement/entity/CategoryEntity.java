package com.pttem.ecommerce.categorymanagement.entity;

import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "tblcategory")
public class CategoryEntity extends AuditEntity {

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;


    //TODO: Parent eklenecek...

}