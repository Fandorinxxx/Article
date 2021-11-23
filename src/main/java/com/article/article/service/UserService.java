package com.article.article.service;

import java.util.Collection;
import java.util.Optional;

import com.article.article.repository.entity.UserEntity;
import com.article.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }

     public UserEntity saveOrUpdate(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }

}
