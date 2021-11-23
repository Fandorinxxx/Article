package com.article.article;

import com.article.article.repository.entity.RoleEntity;
import com.article.article.repository.entity.UserEntity;
import com.article.article.service.RoleService;
import com.article.article.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ArticleApplication implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    public ArticleApplication(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.findAll().isEmpty()) {
            addRole("ADMIN");
            addRole("USER");
        }
        if (userService.findAll().isEmpty()) {
            addUser("user", "user", "USER");
            addUser("admin", "admin", "ADMIN");
        }
    }

    private void addUser(String name, String password, String role) {
        var user = new UserEntity();
        user.setName(name);
        user.setRole(roleService.findByName(role));
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userService.saveOrUpdate(user);
    }

    private void addRole(String role) {
        var roleEntity = new RoleEntity();
        roleEntity.setName(role);
        roleService.saveOrUpdate(roleEntity);
    }

}
