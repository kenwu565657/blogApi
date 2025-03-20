package com.contentfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.contentfarm.persistence", "com.contentfarm.elasticsearch"})
@EnableJpaRepositories(basePackages = {"com.contentfarm.persistence"})
@SpringBootApplication(scanBasePackages = {"com.contentfarm"})
public class ContentFarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentFarmApplication.class, args);
    }
}
