package com.contentfarm.elasticsearch.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.contentfarm.elasticsearch")
@EntityScan(basePackages = "com.contentfarm.elasticsearch")
@SpringBootApplication(scanBasePackages = "com.contentfarm.elasticsearch")
public class TestConfiguration {
}
