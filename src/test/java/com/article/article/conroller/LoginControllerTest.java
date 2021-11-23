package com.article.article.conroller;

import java.util.Optional;

import com.article.article.BaseValues;
import com.article.article.conroller.model.Token;
import com.article.article.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest implements BaseValues {

    @Mock
    JwtTokenProvider jwtTokenProvider;
    LoginController target;

    @BeforeEach
    void setUp() {
        target = new LoginController(jwtTokenProvider);
    }

    @Test
    void shouldPass() {
        var userDto = userDto();
        var expected = new Token("some token");
        when(jwtTokenProvider.getAuthentication(userDto))
                .thenReturn(Optional.of(expected));
        var result = target.login(userDto);
        assertEquals(result.getBody(), expected.toString());
    }

}
