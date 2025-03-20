package com.contentfarm.file.operation.service.blogpost;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BlogPostFileQueryTestConfiguration.class)
class BlogPostFileQueryServiceTest {

    private final Logger logger = LoggerFactory.getLogger(BlogPostFileQueryServiceTest.class);
    private byte[] testingFileContent;

    @Autowired
    BlogPostFileQueryService blogPostFileQueryService;

    @BeforeAll
    void setUp() {
        String TEST_FIlE_CLASS_PATH = "classpath:testing.md";
        try {
            File testingFile = ResourceUtils.getFile(TEST_FIlE_CLASS_PATH);
            testingFileContent = Files.readAllBytes(testingFile.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    void getBlogPostContentByFileName() {
        var fileContent = blogPostFileQueryService.getBlogPostContentByFileName("testing.md");
        Assertions.assertNotNull(fileContent);
        Assertions.assertTrue(fileContent.length > 0);
        Assertions.assertEquals(testingFileContent.length, fileContent.length);
    }
}