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

public class ArticleDtoTest implements BaseValues {

    private static Validator validator;

    @BeforeEach
    public void setUp() {
        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static Stream<Arguments> articleDtoProvider() {
        return Stream.of(
            Arguments.of(new ArticleDto(nullValue, validContent, validPublishDate, validTitle), 1, "author"),
            Arguments.of(new ArticleDto(empty, validContent, validPublishDate, validTitle), 1, "author"),
            Arguments.of(new ArticleDto(blank, validContent, validPublishDate, validTitle), 1, "author"),
            Arguments.of(new ArticleDto(validAuthor, nullValue, validPublishDate, validTitle), 1, "content"),
            Arguments.of(new ArticleDto(validAuthor, empty, validPublishDate, validTitle), 1, "content"),
            Arguments.of(new ArticleDto(validAuthor, blank, validPublishDate, validTitle), 1, "content"),
            Arguments.of(new ArticleDto(validAuthor, validContent, nullValue, validTitle), 1, "publishDate"),
            Arguments.of(new ArticleDto(validAuthor, validContent, empty, validTitle), 1, "publishDate"),
            Arguments.of(new ArticleDto(validAuthor, validContent, blank, validTitle), 1, "publishDate"),
            Arguments.of(new ArticleDto(validAuthor, validContent, validPublishDate, moreThan100), 1, "title"),
            Arguments.of(new ArticleDto(validAuthor, validContent, validPublishDate, empty), 1, "title")
        );
    }

    @ParameterizedTest
    @MethodSource("articleDtoProvider")
    public void shouldFailDto(ArticleDto dto, int expected, String field) {
        var violations = new ArrayList<>(validator.validate(dto));
        assertThat(violations).hasSize(expected);
        assertThat(violations.get(0).getPropertyPath().toString()).isEqualTo(field);
    }

}