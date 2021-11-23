package com.article.article.conroller.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ArticleDto {

    @NotBlank
    String author;
    @NotBlank
    String content;
    @NotBlank
    String publishDate;
    @NonNull
    @Size(max = 100, min = 1)
    String title;

}
