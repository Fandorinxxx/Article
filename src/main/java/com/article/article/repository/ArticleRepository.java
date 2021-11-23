package com.article.article.repository;

import com.article.article.repository.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {

    Page<ArticleEntity> findAll(Pageable pageable);

    List<ArticleEntity> findAllByPublishDateBetween(Long endpointDateBegin, Long endpointDateBeginEnd);
}
