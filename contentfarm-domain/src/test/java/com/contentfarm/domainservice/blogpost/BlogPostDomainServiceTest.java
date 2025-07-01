package com.contentfarm.domainservice.blogpost;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.outputport.blogpost.file.IBlogPostFileQueryService;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceCommandService;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceQueryService;
import com.contentfarm.outputport.blogpost.search.IBlogPostIndexService;
import com.contentfarm.outputport.blogpost.search.IBlogPostSearchService;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogPostDomainServiceTest {
    private final BlogPostDomainService blogPostDomainService =
            new BlogPostDomainService(
                new IBlogPostPersistenceQueryServiceStub(),
                new IBlogPostPersistenceCommandServiceStub(),
                new IBlogPostSearchServiceStub(),
                new IBlogPostIndexServiceStub(),
                new IBlogPostFileQueryServiceStub()
            );

    @Test
    void indexBlogPost() {
    }

    @Test
    void deleteBlogPost() {
    }

    @Test
    void addBlogPostTag() {
    }

    @Test
    void deleteBlogPostTag() {
    }

    @Test
    void getBlogPostById() {
    }

    @Test
    void getBlogPostContentAsMarkdownById() {
    }

    @Test
    void findTagList() {
    }

    @Test
    void searchBlogPostByKeywordAndPageNumberAndPageSize() {
    }

    @Test
    void searchBlogPostByTagList() {
    }

    @Test
    void searchAllBlogPost() {
    }

    private class IBlogPostPersistenceQueryServiceStub implements IBlogPostPersistenceQueryService {

        @Override
        public BlogPostDomainModel getById(String id) {
            return null;
        }

        @Override
        public List<String> findTagList() {
            return List.of();
        }
    }

    private class IBlogPostPersistenceCommandServiceStub implements IBlogPostPersistenceCommandService {

        @Override
        public BlogPostDomainModel upsertBlogPost(BlogPostDomainModel blogPostDomainModel) {
            return null;
        }

        @Override
        public String deleteBlogPostById(String id) {
            return "";
        }

        @Override
        public void addBlogPostTag(List<String> blogPostTagNameList) {

        }

        @Override
        public void deleteBlogPostTag(List<String> blogPostTagNameList) {

        }
    }

    private class IBlogPostSearchServiceStub implements IBlogPostSearchService {

        @Override
        public BlogPostSearchResultDto getBlogPostById(String id) {
            return null;
        }

        @Override
        public SearchResult<BlogPostSearchResultDto> searchAllBlogPost() {
            return null;
        }

        @Override
        public SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
            return null;
        }

        @Override
        public SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(List<String> tagList) {
            return null;
        }
    }

    private class IBlogPostIndexServiceStub implements IBlogPostIndexService {

        @Override
        public void indexBlogPost(BlogPostDomainModel blogPostDomainModel) {

        }

        @Override
        public void deleteBlogPostIndex(String id) {

        }
    }

    private class IBlogPostFileQueryServiceStub implements IBlogPostFileQueryService {

        @Override
        public byte[] getBlogPostContentByFileName(String fileName) {
            return new byte[0];
        }
    }
}