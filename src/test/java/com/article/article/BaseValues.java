package com.article.article;

import com.article.article.conroller.model.ArticleDto;
import com.article.article.conroller.model.UserDto;
import com.article.article.repository.entity.ArticleEntity;
import com.article.article.repository.entity.UserEntity;

import java.sql.Date;


public interface BaseValues {

    String empty = "";
    String blank = " ";
    String moreThan100 = "test".repeat(101);
    String nullValue = null;
    String validAuthor = "Some Author";
    String validContent = "Some content";
    String validPublishDate = "2018-09-16";
    String validTitle = "Hello world";
    String validUserName = "user";
    String validPassword = "user";

    default UserEntity createUser(String name, String password) {
        var user = new UserEntity();
        user.setName(name);
        user.setPassword(password);
        return user;
    }

    default ArticleEntity createArticleEntity(String author, String content, String publishDate, String title) {
        var articleEntity = new ArticleEntity();
        articleEntity.setAuthor(author);
        articleEntity.setContent(content);
        articleEntity.setPublishDate(Date.valueOf(publishDate).getTime());
        articleEntity.setTitle(title);
        return articleEntity;
    }

    default ArticleDto createArticleDto(String author, String content, String publishDate, String title) {
        return ArticleDto.builder()
                .author(author)
                .content(content)
                .publishDate(publishDate)
                .title(title)
                .build();
    }
    default String token(String response){
        return response.substring(18, response.length() - 1);
    }

    default UserDto userDto(String name , String password ){
        return UserDto.builder()
                .name(name)
                .password(password)
                .build();
    }

    default UserDto userDto(){
        return UserDto.builder()
                .name(validUserName)
                .password(validPassword)
                .build();
    }


}
