package com.article.article.conroller;

import com.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final ArticleService articleService;

    @GetMapping(path = "/admin/listArticles")
    public ResponseEntity findAllByPublishDate(String endpointDate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> list = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        boolean isAnonymous = ((SimpleGrantedAuthority) (list.toArray())[0]).getAuthority().equals("ROLE_ANONYMOUS");
        if (!isAnonymous) {
        return new ResponseEntity<>(articleService.findAllByPublishDate(endpointDate), HttpStatus.OK);
        } else {
            logger.error("Invalid USER IS ANONYMOUS");
        }
        return new ResponseEntity<>("Invalid USER IS ANONYMOUS",HttpStatus.UNAUTHORIZED);
    }


}
