package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.constant.BlogPostContentType;
import com.contentfarm.persistence.blogpost.TestContainerRelationalDatabase;
import com.contentfarm.persistence.blogpost.dao.IBlogPostDao;
import com.contentfarm.persistence.blogpost.dao.IBlogPostTagDao;
import com.contentfarm.persistence.blogpost.entity.BlogPostEntity;
import com.contentfarm.persistence.blogpost.entity.BlogPostTagEntity;
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
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(TestContainerRelationalDatabase.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional
class BlogPostPersistenceQueryServiceTest {
    @Autowired
    IBlogPostDao blogPostDao;

    @Autowired
    IBlogPostTagDao blogPostTagDao;

    @Autowired
    BlogPostPersistenceQueryService blogPostPersistenceQueryService;

    @BeforeAll
    void setUp() {
        blogPostDao.deleteAll();
        blogPostTagDao.deleteAll();
        insertTestingBlogPostTagEntity();
    }

    @Test
    void getById() {
        String id = UUID.randomUUID().toString();
        insertTestingBlogPostEntity(id);
        BlogPostDomainModel blogPostDomainModel = blogPostPersistenceQueryService.getById(id);
        Assertions.assertNotNull(blogPostDomainModel);
        Assertions.assertEquals(id, blogPostDomainModel.getId());
        Assertions.assertEquals("testing_blog.md", blogPostDomainModel.getContentFileName());
        Assertions.assertEquals("testingTitle1", blogPostDomainModel.getTitle());
        Assertions.assertEquals("testingAuthorId1", blogPostDomainModel.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, blogPostDomainModel.getContentType());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), blogPostDomainModel.getCreatedDateTime());
    }

    @Test
    void findTagList() {
        var tagNameList = blogPostPersistenceQueryService.findTagList();
        Assertions.assertNotNull(tagNameList);
        Assertions.assertEquals(List.of("Java", "Testing"), tagNameList);
    }

    private void insertTestingBlogPostTagEntity() {
        BlogPostTagEntity blogPostTagEntity1 = new BlogPostTagEntity();
        blogPostTagEntity1.setId(UUID.randomUUID().toString());
        blogPostTagEntity1.setTagName("Java");
        BlogPostTagEntity blogPostTagEntity2 = new BlogPostTagEntity();
        blogPostTagEntity2.setId(UUID.randomUUID().toString());
        blogPostTagEntity2.setTagName("Testing");
        blogPostTagDao.saveAll(List.of(blogPostTagEntity1, blogPostTagEntity2));
    }

    private void insertTestingBlogPostEntity(String id) {
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setId(id);
        blogPostEntity.setContentFileName("testing_blog.md");
        blogPostEntity.setTitle("testingTitle1");
        blogPostEntity.setAuthorId("testingAuthorId1");
        blogPostEntity.setContentType(BlogPostContentType.MARKDOWN);
        blogPostEntity.setCreatedDateTime(LocalDateTime.of(2025, 3, 20, 0, 0, 0));
        blogPostDao.save(blogPostEntity);
    }
}
