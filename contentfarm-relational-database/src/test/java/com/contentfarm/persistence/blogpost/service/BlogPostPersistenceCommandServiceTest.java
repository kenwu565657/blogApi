package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.constant.BlogPostContentType;
import com.contentfarm.persistence.blogpost.TestContainerRelationalDatabase;
import com.contentfarm.persistence.blogpost.dao.IBlogPostDao;
import com.contentfarm.persistence.blogpost.dao.IBlogPostTagDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(TestContainerRelationalDatabase.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional
class BlogPostPersistenceCommandServiceTest {
    @Autowired
    BlogPostPersistenceCommandService  blogPostPersistenceCommandService;

    @Autowired
    IBlogPostDao blogPostDao;

    @Autowired
    IBlogPostTagDao blogPostTagDao;

    @BeforeAll
    void setUp() {
        blogPostDao.deleteAll();
        blogPostTagDao.deleteAll();
    }

    @Test
    void upsertBlogPost() {
        long count = blogPostDao.count();
        Assertions.assertEquals(0, count);

        BlogPostDomainModel blogPostDomainModel = BlogPostDomainModel
                .builder()
                .id(null)
                .title("testingTitle1")
                .summary("testingSummary1")
                .authorId("testingAuthorId")
                .tagList(List.of("Java", "Testing"))
                .coverImageUrl("image.png")
                .contentType(BlogPostContentType.MARKDOWN)
                .contentFileName("content.md")
                .createdDateTime(LocalDateTime.of(2025, 3, 20, 0, 0, 0))
                .build();
        var savedModel = blogPostPersistenceCommandService.upsertBlogPost(blogPostDomainModel);
        String savedBlogPostId = savedModel.getId();
        var blogPostEntity = blogPostDao.getReferenceById(savedModel.getId());
        Assertions.assertNotNull(blogPostEntity);
        Assertions.assertEquals(savedBlogPostId, blogPostEntity.getId());
        Assertions.assertEquals("testingTitle1", blogPostEntity.getTitle());
        Assertions.assertEquals("testingAuthorId", blogPostEntity.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, blogPostEntity.getContentType());
        Assertions.assertEquals("content.md", blogPostEntity.getContentFileName());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), blogPostEntity.getCreatedDateTime());

        savedModel.setTitle("testingTitle2");
        blogPostPersistenceCommandService.upsertBlogPost(savedModel);
        blogPostEntity = blogPostDao.getReferenceById(savedModel.getId());
        Assertions.assertNotNull(blogPostEntity);
        Assertions.assertEquals(savedBlogPostId, blogPostEntity.getId());
        Assertions.assertEquals("testingTitle2", blogPostEntity.getTitle());
        Assertions.assertEquals("testingAuthorId", blogPostEntity.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, blogPostEntity.getContentType());
        Assertions.assertEquals("content.md", blogPostEntity.getContentFileName());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), blogPostEntity.getCreatedDateTime());

        count = blogPostDao.count();
        Assertions.assertEquals(1, count);
        blogPostPersistenceCommandService.deleteBlogPostById(savedBlogPostId);

        count = blogPostDao.count();
        Assertions.assertEquals(0, count);
    }

    @Test
    void addBlogPostTag() {
        long count = blogPostTagDao.count();
        Assertions.assertEquals(0, count);

        var tagList = List.of("Java", "Testing");
        blogPostPersistenceCommandService.addBlogPostTag(tagList);
        count = blogPostTagDao.count();
        Assertions.assertEquals(2, count);

        blogPostPersistenceCommandService.deleteBlogPostTag(tagList);
        count = blogPostTagDao.count();
        Assertions.assertEquals(0, count);
    }

}