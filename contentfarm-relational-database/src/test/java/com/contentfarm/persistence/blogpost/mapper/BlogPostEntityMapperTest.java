package com.contentfarm.persistence.blogpost.mapper;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.constant.BlogPostContentType;
import com.contentfarm.persistence.blogpost.entity.BlogPostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogPostEntityMapperTest {
    private final BlogPostEntityMapper blogPostEntityMapper = new BlogPostEntityMapper();

    @Test
    void mapToBlogPostDomainModel() {
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setId("testingId1");
        blogPostEntity.setContentFileName("testingContent1.md");
        blogPostEntity.setTitle("testingTitle1");
        blogPostEntity.setAuthorId("testingAuthorId1");
        blogPostEntity.setContentType(BlogPostContentType.MARKDOWN);
        blogPostEntity.setCreatedDateTime(LocalDateTime.of(2025, 3, 20, 0, 0, 0));
        BlogPostDomainModel blogPostDomainModel = blogPostEntityMapper.mapToBlogPostDomainModel(blogPostEntity);
        Assertions.assertEquals("testingId1", blogPostDomainModel.getId());
        Assertions.assertEquals("testingContent1.md", blogPostDomainModel.getContentFileName());
        Assertions.assertEquals("testingTitle1", blogPostDomainModel.getTitle());
        Assertions.assertEquals("testingAuthorId1", blogPostDomainModel.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, blogPostDomainModel.getContentType());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), blogPostDomainModel.getCreatedDateTime());
    }

    @Test
    void mapToBlogPostDomainModel_null() {
        BlogPostDomainModel blogPostDomainModel = blogPostEntityMapper.mapToBlogPostDomainModel(null);
        Assertions.assertNull(blogPostDomainModel);
    }

    @Test
    void mapToBlogPostEntity() {
        BlogPostDomainModel blogPostDomainModel = BlogPostDomainModel
                .builder()
                .id("testingId1")
                .title("testingTitle1")
                .summary("testingSummary1")
                .authorId("testingAuthorId")
                .tagList(List.of("Java", "Testing"))
                .coverImageUrl("image.png")
                .contentType(BlogPostContentType.MARKDOWN)
                .contentFileName("content.md")
                .createdDateTime(LocalDateTime.of(2025, 3, 20, 0, 0, 0))
                .build();
        var blogPostEntity = blogPostEntityMapper.mapToBlogPostEntity(blogPostDomainModel);
        Assertions.assertNotNull(blogPostEntity);
        Assertions.assertEquals("testingId1", blogPostEntity.getId());
        Assertions.assertEquals("testingTitle1", blogPostEntity.getTitle());
        Assertions.assertEquals("testingAuthorId", blogPostEntity.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, blogPostEntity.getContentType());
        Assertions.assertEquals("content.md", blogPostEntity.getContentFileName());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), blogPostEntity.getCreatedDateTime());
    }

    @Test
    void mapToBlogPostEntity_null() {
        Assertions.assertNull(blogPostEntityMapper.mapToBlogPostEntity(null));
    }
}