package com.article.article.service;

import java.util.Collection;
import java.util.Optional;

import com.article.article.repository.entity.RoleEntity;
import com.article.article.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Collection<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public RoleEntity saveOrUpdate(RoleEntity role) {
        return roleRepository.saveAndFlush(role);
    }

}
