package com.yifan.models.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(updatable = false)
    private Date createdAt;

    @Column
    private Date updatedAt;

}