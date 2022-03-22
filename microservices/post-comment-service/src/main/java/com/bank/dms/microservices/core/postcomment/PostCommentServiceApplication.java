package com.bank.dms.microservices.core.postcomment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.bank.dms" })
@EnableJpaRepositories(basePackages = {"com.bank.dms.dao.repository"})
@EntityScan(basePackages = {"com.bank.dms.dao.model"})
public class PostCommentServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(PostCommentServiceApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PostCommentServiceApplication.class, args);
    }
}