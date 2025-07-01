package com.contentfarm.domainservice.blogpost;

import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostIndexServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostPersistenceCommandServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostPersistenceQueryServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostSearchServiceStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShortBlogPostDomainServiceTest {
    private final ShortBlogPostDomainService shortBlogPostDomainService = new ShortBlogPostDomainService(
        new ShortBlogPostPersistenceCommandServiceStub(),
        new ShortBlogPostPersistenceQueryServiceStub(),
        new ShortBlogPostIndexServiceStub(),
        new ShortBlogPostSearchServiceStub()
    );

    @Test
    void indexShortBlogPost() {
    }

    @Test
    void deleteShortBlogPost() {
    }

    @Test
    void getShortBlogPostById() {
    }

    @Test
    void getBlogPostContentAsMarkdownById() {
    }

    @Test
    void searchShortBlogPostById() {
    }

    @Test
    void searchShortBlogPostByKeywordAndPageNumberAndPageSize() {
    }

    @Test
    void searchShortBlogPostByTagList() {
    }

    @Test
    void searchAllShortBlogPost() {
    }
}