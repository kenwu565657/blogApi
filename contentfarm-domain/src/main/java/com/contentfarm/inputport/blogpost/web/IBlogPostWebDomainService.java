package com.contentfarm.inputport.blogpost.web;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;

import java.util.List;

public interface IBlogPostWebDomainService {
    BlogPostDomainModel getBlogPostById(String id);
    byte[] getBlogPostContentAsMarkdownById(String Id);
    List<String> findTagList();
    SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize);
    SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(List<String> tagList);
    SearchResult<BlogPostSearchResultDto> searchAllBlogPost();
}
