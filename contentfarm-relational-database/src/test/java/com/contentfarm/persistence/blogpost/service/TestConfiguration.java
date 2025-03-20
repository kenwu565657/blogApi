package com.contentfarm.persistence.blogpost.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.contentfarm.persistence"})
@EntityScan(basePackages = "com.contentfarm.persistence")
@SpringBootApplication(scanBasePackages = "com.contentfarm.persistence")
public class TestConfiguration {
}
