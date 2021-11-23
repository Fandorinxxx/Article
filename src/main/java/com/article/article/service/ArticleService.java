package com.article.article.service;

import com.article.article.conroller.model.ArticleDto;
import com.article.article.repository.ArticleRepository;
import com.article.article.repository.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
public class ArticleService {
    private final int ADMIN_DAY_ENDPOINT = 6;
    private final ArticleRepository articleRepository;

    public void save(ArticleDto articleDto) {
        articleRepository.save(ArticleEntity.of(articleDto));
    }

    public Page<ArticleEntity> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Map<LocalDate, Long> findAllByPublishDate(String endpointDate) {
        LocalDate endpointDateSql = LocalDate.parse(endpointDate);
        Instant instantendpointDateEnd = endpointDateSql.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long endpointDateEndLong = instantendpointDateEnd.toEpochMilli();
        LocalDate endpointDateStart = endpointDateSql.minusDays(ADMIN_DAY_ENDPOINT);
        Instant instantendpointDateStart = endpointDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long endpointDateStartLong = instantendpointDateStart.toEpochMilli();
        List<ArticleEntity> Result = articleRepository.findAllByPublishDateBetween(endpointDateStartLong, endpointDateEndLong);

        Map<LocalDate, Long> articleAdminResult = new LinkedHashMap<>();
        for (Long i = endpointDateStartLong; i <= endpointDateEndLong; i = i + 24 * 60 * 60 * 1000) {
            LocalDate localDate = LocalDate.ofInstant(Instant.ofEpochMilli(i), ZoneId.systemDefault());
            articleAdminResult.put(localDate, 0L);
        }
        Result.stream().forEach(e -> {
            articleAdminResult.put(
                    LocalDate.ofInstant(Instant.ofEpochMilli(e.getPublishDate()), ZoneId.systemDefault()),
                    articleAdminResult.get(LocalDate.ofInstant(Instant.ofEpochMilli(e.getPublishDate()), ZoneId.systemDefault())) + 1

            );
        });
        return articleAdminResult;
    }

}
