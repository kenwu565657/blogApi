package com.contentfarm.domainservice.blogpost;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.inputport.blogpost.web.IAdminBlogPostCommandService;
import com.contentfarm.inputport.blogpost.web.IBlogPostWebDomainService;
import com.contentfarm.outputport.blogpost.file.IBlogPostFileQueryService;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceCommandService;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceQueryService;
import com.contentfarm.outputport.blogpost.search.IBlogPostIndexService;
import com.contentfarm.outputport.blogpost.search.IBlogPostSearchService;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostDomainService implements IBlogPostWebDomainService, IAdminBlogPostCommandService {
    private final IBlogPostPersistenceQueryService blogPostPersistenceQueryService;
    private final IBlogPostPersistenceCommandService blogPostPersistenceCommandService;
    private final IBlogPostSearchService blogPostSearchService;
    private final IBlogPostIndexService blogPostIndexService;
    private final IBlogPostFileQueryService blogPostFileQueryService;

    @Override
    public void indexBlogPost(BlogPostDomainModel blogPostDomainModel) {
        BlogPostDomainModel savedBlogPostDomainModel;
        try {
            savedBlogPostDomainModel = blogPostPersistenceCommandService.upsertBlogPost(blogPostDomainModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            blogPostIndexService.indexBlogPost(savedBlogPostDomainModel);
        } catch (Exception e) {
            blogPostPersistenceCommandService.deleteBlogPostById(savedBlogPostDomainModel.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBlogPost(String blogPostId) {
        BlogPostSearchResultDto blogPostIndexToBeDelete;
        try {
            blogPostIndexToBeDelete = blogPostSearchService.getBlogPostById(blogPostId);
            blogPostIndexService.deleteBlogPostIndex(blogPostId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            blogPostPersistenceCommandService.deleteBlogPostById(blogPostId);
        } catch (Exception e) {
            BlogPostDomainModel blogPostDomainModel = toBlogPostDomainModel(blogPostIndexToBeDelete);
            blogPostIndexService.indexBlogPost(blogPostDomainModel);
            throw new RuntimeException(e);
        }
    }

    private BlogPostDomainModel toBlogPostDomainModel(BlogPostSearchResultDto blogPostIndexToBeDelete) {
        BlogPostDomainModel blogPostDomainModel = new BlogPostDomainModel();
        blogPostDomainModel.setId(blogPostIndexToBeDelete.getId());
        blogPostDomainModel.setTitle(blogPostIndexToBeDelete.getTitle());
        blogPostDomainModel.setTagList(blogPostIndexToBeDelete.getTagList());
        blogPostDomainModel.setSummary(blogPostIndexToBeDelete.getSummary());
        blogPostDomainModel.setAuthorId(blogPostIndexToBeDelete.getAuthorName());
        blogPostDomainModel.setCoverImageUrl(blogPostIndexToBeDelete.getImageUrl());
        blogPostDomainModel.setCreatedDateTime(LocalDateTime.parse(blogPostIndexToBeDelete.getPostDate()));
        return blogPostDomainModel;
    }

    @Override
    public void addBlogPostTag(List<String> blogPostTagNameList) {
        blogPostPersistenceCommandService.addBlogPostTag(blogPostTagNameList);
    }

    @Override
    public void deleteBlogPostTag(List<String> blogPostTagNameList) {
        blogPostPersistenceCommandService.deleteBlogPostTag(blogPostTagNameList);
    }

    @Override
    public BlogPostDomainModel getBlogPostById(String id) {
        return blogPostPersistenceQueryService.getById(id);
    }

    @Override
    public byte[] getBlogPostContentAsMarkdownById(String Id) {
        var blogPostDomainModel = blogPostPersistenceQueryService.getById(Id);
        return blogPostFileQueryService.getBlogPostContentByFileName(blogPostDomainModel.getContentFileName());
    }

    @Override
    public List<String> findTagList() {
        return blogPostPersistenceQueryService.findTagList();
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
        return blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize(keyword, pageNumber, pageSize);
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(List<String> tagList) {
        return blogPostSearchService.searchBlogPostByTagList(tagList);
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchAllBlogPost() {
        return blogPostSearchService.searchAllBlogPost();
    }
}
