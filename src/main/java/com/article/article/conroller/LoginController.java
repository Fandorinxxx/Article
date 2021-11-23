package com.article.article.conroller;


import com.article.article.security.JwtTokenProvider;
import com.article.article.conroller.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserDto userDto) {
        return jwtTokenProvider.getAuthentication(userDto)
            .map(token -> ResponseEntity.ok().body(token.toString()))
            .orElseGet(() -> ResponseEntity.badRequest().body("Failed"));
    }

    @DeleteMapping(value = "/logout")
    public void logout() {
    }

}
