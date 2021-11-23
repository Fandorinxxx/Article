package com.article.article.conroller.model;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    @NotBlank
    String name;
    @NotBlank
    String password;

}
