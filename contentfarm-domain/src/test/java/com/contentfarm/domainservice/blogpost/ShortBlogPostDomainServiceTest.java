package com.contentfarm.domainservice.blogpost;

import com.contentfarm.domainservice.mock.blogpost.persistence.BlogPostFileQueryServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.FakeShortBlogPostDatabase;
import com.contentfarm.domainservice.mock.blogpost.persistence.FakeShortBlogPostElasticSearch;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostIndexServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostPersistenceCommandServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostPersistenceQueryServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.ShortBlogPostSearchServiceStub;
import com.contentfarm.domainservice.mock.blogpost.persistence.TestingShortBlogPostDomainModelGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class ShortBlogPostDomainServiceTest {
    private final ShortBlogPostDomainService shortBlogPostDomainService = new ShortBlogPostDomainService(
        new ShortBlogPostPersistenceCommandServiceStub(),
        new ShortBlogPostPersistenceQueryServiceStub(),
        new ShortBlogPostIndexServiceStub(),
        new ShortBlogPostSearchServiceStub(),
        new BlogPostFileQueryServiceStub()
    );

    @BeforeEach
    void clearUp() {
        FakeShortBlogPostDatabase.INSTANCE.getMockDatabase().clear();
        FakeShortBlogPostElasticSearch.INSTANCE.getShortBlogPostSearchResultDtoHashMap().clear();
    }

    @Test
    void indexShortBlogPost() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        Assertions.assertDoesNotThrow(() -> shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel));
    }

    @Test
    void searchShortBlogPostById() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        var result = shortBlogPostDomainService.searchShortBlogPostById(shortBlogPostDomainModel.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(shortBlogPostDomainModel.getId(), result.getId());
    }

    @Test
    void searchShortBlogPostByKeywordAndPageNumberAndPageSize() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        var result = shortBlogPostDomainService.searchShortBlogPostByKeywordAndPageNumberAndPageSize(TestingShortBlogPostDomainModelGenerator.TESTING_SHORT_BLOG_POST_SEARCH_KEYWORD, 1, 1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getSearchResultCount());
    }

    @Test
    void searchShortBlogPostByTagList() {
        var tagList = List.of(TestingShortBlogPostDomainModelGenerator.TESTING_SHORT_BLOG_POST_TAG_1);
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        var result = shortBlogPostDomainService.searchShortBlogPostByTagList(tagList);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getSearchResultCount());
    }

    @Test
    void searchAllShortBlogPost() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        var result = shortBlogPostDomainService.searchAllShortBlogPost();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getSearchResultCount());
    }

    @Test
    void deleteShortBlogPost() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        shortBlogPostDomainService.deleteShortBlogPost(shortBlogPostDomainModel.getId());
    }

    @Test
    void getShortBlogPostById() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        var result = shortBlogPostDomainService.getShortBlogPostById(shortBlogPostDomainModel.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(shortBlogPostDomainModel.getId(), result.getId());
    }

    @Test
    void getBlogPostContentAsMarkdownById() {
        var shortBlogPostDomainModel = TestingShortBlogPostDomainModelGenerator.generateShortBlogPostDomainModel();
        shortBlogPostDomainModel.setContentFileName("test-content.md");
        shortBlogPostDomainService.indexShortBlogPost(shortBlogPostDomainModel);
        byte[] content = shortBlogPostDomainService.getBlogPostContentAsMarkdownById(shortBlogPostDomainModel.getId());
        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.length > 0, "Content should not be empty");
    }
}