package com.article.article.service;

import com.article.article.BaseValues;
import com.article.article.repository.ArticleRepository;
import com.article.article.repository.entity.ArticleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest implements BaseValues {

    ArticleService target;
    @Mock
    ArticleRepository articleRepository;


    @BeforeEach
    void setUp() {
        target = new ArticleService(articleRepository);
    }

    @Test
    void save() {
        var articleDto = createArticleDto("author", "content", "2020-10-10", "title");
        var expected = createArticleEntity("author", "content", "2020-10-10", "title");
        when(articleRepository.save(expected)).thenReturn(expected);
        assertDoesNotThrow(() -> target.save(articleDto));
    }

    @Test
    void findAll() {
        PageRequest pageable = PageRequest.of(0, 10);
        var expected = new PageImpl<ArticleEntity>(List.of(new ArticleEntity()), pageable, 10);
        when(articleRepository.findAll(pageable)).thenReturn(expected);
        var actual = target.findAll(pageable);
        assertEquals(actual, expected);
    }

    @Test
    void findAllByPublishDate() {
        var expected = List.of(createArticleEntity("author", "content", "2020-10-10", "title"));
        var endpointDateStartLong = 1601758800000L; //2020-10-10
        var endpointDateEndLong = 1602277200000L;  //2020-10-04
        var requestDate = "2020-10-10";
        when(articleRepository.findAllByPublishDateBetween(endpointDateStartLong, endpointDateEndLong)).thenReturn(expected);
        var expectedMap = new HashMap<LocalDate, Long>();
        expectedMap.put(LocalDate.of(2020, 10, 4), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 5), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 6), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 7), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 8), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 9), 0L);
        expectedMap.put(LocalDate.of(2020, 10, 10), 1L);
        var actual = target.findAllByPublishDate(requestDate);
        assertEquals(expectedMap, actual);
    }


}