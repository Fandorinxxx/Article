package com.article.article.repository.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Singular;

@Entity
@Data
@Table(name = "role_t")
public class RoleEntity {

    @Id
    @GeneratedValue
    Long id;
    String name;
    @OneToMany(targetEntity = UserEntity.class, mappedBy = "role",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Singular
    Set<UserEntity> users;

}
