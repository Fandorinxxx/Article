package com.article.article.conroller.model;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Token {

    private String accessToken;
    private static final String KEY_FORMAT = "Bearer::%s";
    private static final Pattern SEPARATOR = Pattern.compile("::");

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

   /* @Override
    public String toString() {
        return withPrefix();
    }

    @JsonIgnore
    public String withPrefix() {
        return String.format(KEY_FORMAT, accessToken);
    }*/

}
