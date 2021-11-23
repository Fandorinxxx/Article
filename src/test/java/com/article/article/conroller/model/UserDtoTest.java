package com.article.article.conroller.model;

import java.util.ArrayList;
import java.util.stream.Stream;

import javax.validation.Validation;
import javax.validation.Validator;

import com.article.article.BaseValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest implements BaseValues {

    private static Validator validator;

    private static Stream<Arguments> articleDtoProvider() {
        return Stream.of(
            Arguments.of(new UserDto(nullValue, validPassword), 1, "name"),
            Arguments.of(new UserDto(empty, validPassword), 1, "name"),
            Arguments.of(new UserDto(blank, validPassword), 1, "name"),
            Arguments.of(new UserDto(validUserName, nullValue), 1, "password"),
            Arguments.of(new UserDto(validUserName, empty), 1, "password"),
            Arguments.of(new UserDto(validUserName, blank), 1, "password")
        );
    }

    @BeforeEach
    public void setUp() {
        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("articleDtoProvider")
    public void shouldFailDto(UserDto dto, int expected, String field) {
        var violations = new ArrayList<>(validator.validate(dto));
        assertThat(violations).hasSize(expected);
        assertThat(violations.get(0).getPropertyPath().toString()).isEqualTo(field);
    }

}