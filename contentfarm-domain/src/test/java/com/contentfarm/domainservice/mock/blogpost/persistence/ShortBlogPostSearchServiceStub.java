package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.outputport.blogpost.search.IShortBlogPostSearchService;
import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;

import java.util.HashMap;
import java.util.List;

public class ShortBlogPostSearchServiceStub implements IShortBlogPostSearchService {
    @Override
    public ShortBlogPostSearchResultDto getShortBlogPostById(String id) {
        return getMockElasticSearch().get(id);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost() {
        var recordList = getAllRecord();
        return toSearchResult(recordList);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
        var recordList = getAllRecord();
        var filteredRecordList = recordList.stream().filter(x -> x.getTitle().contains(keyword)).toList();
        return toSearchResult(filteredRecordList);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByTagList(List<String> tagList) {
        var recordList = getAllRecord();
        var filteredRecordList = recordList.stream().filter(x -> x.getTagList().containsAll(tagList)).toList();
        return toSearchResult(filteredRecordList);
    }

    private SearchResult<ShortBlogPostSearchResultDto> toSearchResult(List<ShortBlogPostSearchResultDto> resultList) {
        SearchResult<ShortBlogPostSearchResultDto> searchResult = new SearchResult<>();
        searchResult.setSearchResultCount(resultList.size());
        searchResult.setSearchResultItemList(resultList);
        return searchResult;
    }

    private List<ShortBlogPostSearchResultDto> getAllRecord() {
        return getMockElasticSearch().values().stream().toList();
    }

    private HashMap<String, ShortBlogPostSearchResultDto> getMockElasticSearch() {
        return FakeShortBlogPostElasticSearch.INSTANCE.getShortBlogPostSearchResultDtoHashMap();
    }
}
