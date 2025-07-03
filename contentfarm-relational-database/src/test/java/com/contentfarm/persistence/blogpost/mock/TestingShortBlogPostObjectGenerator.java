package com.contentfarm.persistence.blogpost.mock;

import com.contentfarm.ContentFarmLocaleDateTimeUtils;
import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.constant.BlogPostContentType;
import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;

public class TestingShortBlogPostObjectGenerator {
    private TestingShortBlogPostObjectGenerator() {}

    public static ShortBlogPostEntity createShortBlogPostEntity() {
        return ShortBlogPostEntity.builder()
                .id(TESTING_SHORT_BLOG_POST_ID)
                .title("Test Title")
                .contentType(BlogPostContentType.MARKDOWN)
                .authorId("Test Author")
                .createdDateTime(ContentFarmLocaleDateTimeUtils.ofNow())
                .lastUpdatedDateTime(null)
                .build();
    }

    private final static String TESTING_SHORT_BLOG_POST_ID = "testing-short-blog-post-id";

    public static ShortBlogPostDomainModel createShortBlogPostDomainModel() {
        return ShortBlogPostDomainModel.builder()
                .title("Test Title")
                .contentType(BlogPostContentType.MARKDOWN)
                .authorId("Test Author")
                .createdDateTime(ContentFarmLocaleDateTimeUtils.ofNow())
                .build();
    }
}
