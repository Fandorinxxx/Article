package com.article.article.service;

import com.article.article.BaseValues;
import com.article.article.repository.UserRepository;
import com.article.article.repository.entity.RoleEntity;
import com.article.article.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest implements BaseValues {

    @Mock
    UserRepository userRepository;
    UserDetailsServiceImpl target;

    @BeforeEach
    void setUp() {
        target = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void shouldThrowException() {
        var userName = "user";
        when(userRepository.findByName(userName)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()->target.loadUserByUsername(userName));
    }

    @Test
    void shouldPass() {
        var userName = "user";
        var userEntity = createUser(userName, userName);
        var roleEntity = new RoleEntity();
        var userRole = "USER";
        roleEntity.setName(userRole);
        userEntity.setRole(roleEntity);
        when(userRepository.findByName(userName)).thenReturn(userEntity);
        var actual = target.loadUserByUsername(userName);
        assert actual.getAuthorities().contains(new SimpleGrantedAuthority(userRole));
        assert actual.getUsername().equals(userName);


    }

}