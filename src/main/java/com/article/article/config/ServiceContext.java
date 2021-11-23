package com.article.article.config;

import com.article.article.repository.ArticleRepository;
import com.article.article.repository.RoleRepository;
import com.article.article.repository.UserRepository;
import com.article.article.service.ArticleService;
import com.article.article.service.RoleService;
import com.article.article.service.UserDetailsServiceImpl;
import com.article.article.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceContext {

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public ArticleService articleService(ArticleRepository articleRepository) {
        return new ArticleService(articleRepository);
    }

}
