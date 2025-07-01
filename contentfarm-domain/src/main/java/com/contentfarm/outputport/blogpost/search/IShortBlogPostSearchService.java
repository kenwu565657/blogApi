package com.contentfarm.outputport.blogpost.search;

import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;

import java.util.List;

public interface IShortBlogPostSearchService {
    ShortBlogPostSearchResultDto getShortBlogPostById(String id);
    SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost();
    SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize);
    SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByTagList(List<String> tagList);
}
