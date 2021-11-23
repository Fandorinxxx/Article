package com.article.article.service;

import java.util.List;

import com.article.article.BaseValues;
import com.article.article.repository.UserRepository;
import com.article.article.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest implements BaseValues {

    UserService target;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        target = new UserService(userRepository);
    }


    @Test
    void saveOrUpdate() {
        var userFromDto = createUser("user", "user");
        var expected = createUser("user", "user");
        expected.setId(1L);
        when(userRepository.saveAndFlush(userFromDto)).thenReturn(expected);
        var actual = target.saveOrUpdate(userFromDto);

        assertEquals(expected, actual);

    }

    @Test
    void findAll() {
        var expected = List.of(new UserEntity());
        when(userRepository.findAll()).thenReturn(expected);
        var actual = target.findAll();

        assertEquals(expected, actual);

    }


}