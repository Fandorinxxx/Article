package com.article.article.conroller;

import com.article.article.BaseFunctionalTest;
import com.article.article.BaseValues;
import com.article.article.security.JwtTokenProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class LoginControllerFunctionalTest extends BaseFunctionalTest implements BaseValues {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldPass() throws Exception {
        var result = loginWithCorrectData(userDto());
        assert !result.isEmpty();
        assertEquals(jwtTokenProvider.getUserNameFromJwt(token(result)), validUserName);
    }

    @Test
    public void shouldReturnUnauthorized() throws Exception {
        var userDto = userDto("user1", validPassword);
        assert loginWithError(userDto).isEmpty();
    }

}