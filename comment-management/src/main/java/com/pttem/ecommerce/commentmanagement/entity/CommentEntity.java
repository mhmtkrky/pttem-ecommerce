package com.pttem.ecommerce.commentmanagement.entity;

import entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "tblcomment")
public class CommentEntity extends AuditEntity {

    @Min(1)
    @Max(5)
    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "userUUID", nullable = false)
    private Long userUUID;

    @Column(name = "purchasingUUID", nullable = false)
    private Long purchasingUUID;
}