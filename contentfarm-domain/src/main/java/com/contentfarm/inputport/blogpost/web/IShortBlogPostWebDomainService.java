package com.contentfarm.inputport.blogpost.web;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;

import java.util.List;

public interface IShortBlogPostWebDomainService {
    ShortBlogPostDomainModel getShortBlogPostById(String id);
    byte[] getBlogPostContentAsMarkdownById(String Id);
    ShortBlogPostSearchResultDto searchShortBlogPostById(String id);
    SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize);
    SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByTagList(List<String> tagList);
    SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost();
}
