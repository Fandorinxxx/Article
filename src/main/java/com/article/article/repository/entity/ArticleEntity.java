package com.article.article.repository.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.article.article.conroller.model.ArticleDto;
import lombok.Data;

@Entity
@Data
@Table(name = "arcticle")
public class ArticleEntity {

    String author;
    String content;
    @Id
    @GeneratedValue
    Long id;
    long publishDate;
    @Size(max = 100, min = 1)
    String title;

    public static ArticleEntity of(ArticleDto articleDto) {
        var articleEntity = new ArticleEntity();
        articleEntity.setAuthor(articleDto.getAuthor());
        articleEntity.setContent(articleDto.getContent());
        articleEntity.setPublishDate(Date.valueOf(articleDto.getPublishDate()).getTime());
        articleEntity.setTitle(articleDto.getTitle());
        return articleEntity;
    }

}
