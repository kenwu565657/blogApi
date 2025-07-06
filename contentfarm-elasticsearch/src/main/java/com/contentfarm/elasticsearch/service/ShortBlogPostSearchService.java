package com.contentfarm.elasticsearch.service;

import com.contentfarm.outputport.blogpost.search.IShortBlogPostSearchService;
import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortBlogPostSearchService implements IShortBlogPostSearchService {


    @Override
    public ShortBlogPostSearchResultDto getShortBlogPostById(String id) {
        return null;
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost() {
        return null;
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByTagList(List<String> tagList) {
        return null;
    }
}
