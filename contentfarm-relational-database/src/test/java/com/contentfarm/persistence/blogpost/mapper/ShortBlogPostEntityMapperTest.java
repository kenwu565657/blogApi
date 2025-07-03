package com.contentfarm.persistence.blogpost.mapper;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.constant.BlogPostContentType;
import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class ShortBlogPostEntityMapperTest {

    @Test
    void mapToShortBlogPostDomainModel() {
        ShortBlogPostEntityMapper mapper = new ShortBlogPostEntityMapper();

        ShortBlogPostEntity shortBlogPostEntity = new ShortBlogPostEntity();
        shortBlogPostEntity.setId("testingId1");
        shortBlogPostEntity.setContentFileName("testingContent1.md");
        shortBlogPostEntity.setTitle("testingTitle1");
        shortBlogPostEntity.setAuthorId("testingAuthorId1");
        shortBlogPostEntity.setContentType(BlogPostContentType.MARKDOWN);
        shortBlogPostEntity.setCreatedDateTime(LocalDateTime.of(2025, 3, 20, 0, 0, 0));
        ShortBlogPostDomainModel shortBlogPostDomainModel = mapper.mapToShortBlogPostDomainModel(shortBlogPostEntity);
        Assertions.assertEquals("testingId1", shortBlogPostDomainModel.getId());
        Assertions.assertEquals("testingContent1.md", shortBlogPostDomainModel.getContentFileName());
        Assertions.assertEquals("testingTitle1", shortBlogPostDomainModel.getTitle());
        Assertions.assertEquals("testingAuthorId1", shortBlogPostDomainModel.getAuthorId());
        Assertions.assertEquals(BlogPostContentType.MARKDOWN, shortBlogPostDomainModel.getContentType());
        Assertions.assertEquals(LocalDateTime.of(2025, 3, 20, 0, 0, 0), shortBlogPostDomainModel.getCreatedDateTime());
    }

    @Test
    void mapToShortBlogPostDomainModel_null() {
        ShortBlogPostEntityMapper mapper = new ShortBlogPostEntityMapper();
        ShortBlogPostDomainModel shortBlogPostDomainModel = mapper.mapToShortBlogPostDomainModel(null);
        Assertions.assertNull(shortBlogPostDomainModel);
    }

    @Test
    void mapToBlogPostEntity() {
        ShortBlogPostEntityMapper mapper = new ShortBlogPostEntityMapper();

        ShortBlogPostDomainModel shortBlogPostDomainModel = ShortBlogPostDomainModel
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
        var blogPostEntity = mapper.mapToBlogPostEntity(shortBlogPostDomainModel);
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
        ShortBlogPostEntityMapper mapper = new ShortBlogPostEntityMapper();
        ShortBlogPostEntity shortBlogPostEntity = mapper.mapToBlogPostEntity(null);
        Assertions.assertNull(shortBlogPostEntity);
    }
}