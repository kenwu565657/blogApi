package com.contentfarm.elasticsearch.mapper;

import com.contentfarm.ContentFarmLocaleDateTimeUtils;
import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.elasticsearch.document.BlogPostDocument;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlogPostSearchMapper {

    public SearchResult<BlogPostSearchResultDto> toSearchResult(SearchHits<BlogPostDocument> blogPostSearchHits) {
        var searchResult = new SearchResult<BlogPostSearchResultDto>();

        var blogPostSearchResultDtoList = toBlogPostSearchResultDto(blogPostSearchHits);
        searchResult.setSearchResultItemList(blogPostSearchResultDtoList);

        searchResult.setSearchResultCount((int) blogPostSearchHits.getTotalHits());
        searchResult.setSearchExecutionTimeInMs(blogPostSearchHits.getExecutionDuration().toMillis());
        searchResult.setMaxSearchScore(blogPostSearchHits.getMaxScore());

        return searchResult;
    }

    public BlogPostSearchResultDto toBlogPostSearchResultDto(BlogPostDocument blogPostDocument) {
        var blogPostSearchResultDto = new BlogPostSearchResultDto();
        if (null == blogPostDocument) {
            return null;
        }
        blogPostSearchResultDto.setId(blogPostDocument.getId());
        blogPostSearchResultDto.setTitle(blogPostDocument.getTitle());
        blogPostSearchResultDto.setTagList(null == blogPostDocument.getTagList() ? List.of() : blogPostDocument.getTagList());
        blogPostSearchResultDto.setSummary(blogPostDocument.getSummary());
        blogPostSearchResultDto.setAuthorName(blogPostDocument.getAuthorName());
        blogPostSearchResultDto.setPostDate(blogPostDocument.getPostDate());
        blogPostSearchResultDto.setImageUrl(blogPostDocument.getImageUrl());
        return blogPostSearchResultDto;
    }

    public BlogPostDocument toBlogPostDocument(BlogPostDomainModel blogPostDomainModel) {
        return BlogPostDocument
                .builder()
                .id(blogPostDomainModel.getId())
                .title(blogPostDomainModel.getTitle())
                .tagList(blogPostDomainModel.getTagList())
                .summary(blogPostDomainModel.getSummary())
                .authorName(blogPostDomainModel.getAuthorId())
                .postDate(ContentFarmLocaleDateTimeUtils.formatTo_yyyy_MM_dd(blogPostDomainModel.getCreatedDateTime()))
                .imageUrl(blogPostDomainModel.getCoverImageUrl())
                .build();
    }

    public BlogPostDomainModel toBlogPostDomainModel(BlogPostDocument blogPostDocument) {
        return BlogPostDomainModel
                .builder()
                .id(blogPostDocument.getId())
                .title(blogPostDocument.getTitle())
                .tagList(blogPostDocument.getTagList())
                .summary(blogPostDocument.getSummary())
                .authorId(blogPostDocument.getAuthorName())
                .createdDateTime(ContentFarmLocaleDateTimeUtils.parseFrom_yyyy_MM_dd(blogPostDocument.getPostDate()))
                .coverImageUrl(blogPostDocument.getImageUrl())
                .build();
    }

    private List<BlogPostSearchResultDto> toBlogPostSearchResultDto(SearchHits<BlogPostDocument> searchHits) {
        List<BlogPostSearchResultDto> blogPostSearchResultDtoList = new ArrayList<>();
        if (searchHits.getTotalHits() > 0) {
            blogPostSearchResultDtoList = searchHits.getSearchHits().stream().map(x -> toBlogPostSearchResultDto(x.getContent())).toList();
        }
        return blogPostSearchResultDtoList;
    }
}
