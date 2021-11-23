package com.article.article.conroller;

import com.article.article.conroller.model.ArticleDto;
import com.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;


    @PostMapping(path = "/article/add")
    public ResponseEntity addArticle(@RequestBody ArticleDto articleDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> list = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        boolean isAnonymous = ((SimpleGrantedAuthority) (list.toArray())[0]).getAuthority().equals("ROLE_ANONYMOUS");
        if (!isAnonymous) {
            articleService.save(articleDto);
            return new ResponseEntity("Article added Successfully !!! ",HttpStatus.OK);
        } else {
            logger.error("Invalid USER IS ANONYMOUS");
            return new ResponseEntity("Invalid USER IS ANONYMOUS !!! ",HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/article/list")
    public ResponseEntity findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return new ResponseEntity<>(articleService.findAll(
                PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
                )), HttpStatus.OK);
    }
}
