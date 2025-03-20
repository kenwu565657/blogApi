package com.contentfarm.outputport.blogpost.search;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;

import java.util.List;

public interface IBlogPostSearchService {
    BlogPostSearchResultDto getBlogPostById(String id);
    SearchResult<BlogPostSearchResultDto> searchAllBlogPost();
    SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize);
    SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(List<String> tagList);
}
