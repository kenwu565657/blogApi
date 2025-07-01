package com.contentfarm.domainservice.blogpost;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.inputport.blogpost.web.IAdminShortBlogPostCommandService;
import com.contentfarm.inputport.blogpost.web.IShortBlogPostWebDomainService;
import com.contentfarm.outputport.blogpost.file.IBlogPostFileQueryService;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceCommandService;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceQueryService;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostIndexService;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostSearchService;
import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortBlogPostDomainService implements IShortBlogPostWebDomainService, IAdminShortBlogPostCommandService {
    private final IShortBlogPostPersistenceCommandService shortBlogPostPersistenceCommandService;
    private final IShortBlogPostPersistenceQueryService shortBlogPostPersistenceQueryService;
    private final IShortBlogPostIndexService shortBlogPostIndexService;
    private final IShortBlogPostSearchService shortBlogPostSearchService;
    private final IBlogPostFileQueryService blogPostFileQueryService;

    @Override
    public void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        ShortBlogPostDomainModel savedShortBlogPostDomainModel;
        try {
            savedShortBlogPostDomainModel = shortBlogPostPersistenceCommandService.upsertShortBlogPost(shortBlogPostDomainModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            shortBlogPostIndexService.indexShortBlogPost(savedShortBlogPostDomainModel);
        } catch (Exception e) {
            shortBlogPostPersistenceCommandService.deleteShortBlogPostById(savedShortBlogPostDomainModel.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteShortBlogPost(String shortBlogPostId) {
        ShortBlogPostSearchResultDto shortBlogPostIndexToBeDelete;
        try {
            shortBlogPostIndexToBeDelete = shortBlogPostSearchService.getShortBlogPostById(shortBlogPostId);
            shortBlogPostIndexService.deleteShortBlogPostIndex(shortBlogPostId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            shortBlogPostPersistenceCommandService.deleteShortBlogPostById(shortBlogPostId);
        } catch (Exception e) {
            ShortBlogPostDomainModel shortBlogPostDomainModel = toShortBlogPostDomainModel(shortBlogPostIndexToBeDelete);
            shortBlogPostIndexService.indexShortBlogPost(shortBlogPostDomainModel);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShortBlogPostDomainModel getShortBlogPostById(String id) {
        return shortBlogPostPersistenceQueryService.getById(id);
    }

    @Override
    public byte[] getBlogPostContentAsMarkdownById(String Id) {
        var shortBlogPostDomainModel = shortBlogPostPersistenceQueryService.getById(Id);
        return blogPostFileQueryService.getBlogPostContentByFileName(shortBlogPostDomainModel.getContentFileName());
    }

    @Override
    public ShortBlogPostSearchResultDto searchShortBlogPostById(String id) {
        return shortBlogPostSearchService.getShortBlogPostById(id);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
        return shortBlogPostSearchService.searchShortBlogPostByKeywordAndPageNumberAndPageSize(keyword, pageNumber, pageSize);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchShortBlogPostByTagList(List<String> tagList) {
        return shortBlogPostSearchService.searchShortBlogPostByTagList(tagList);
    }

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost() {
        return shortBlogPostSearchService.searchAllShortBlogPost();
    }

    private ShortBlogPostDomainModel toShortBlogPostDomainModel(ShortBlogPostSearchResultDto shortBlogPostSearchResultDto) {
        ShortBlogPostDomainModel shortBlogPostDomainModel = new ShortBlogPostDomainModel();
        shortBlogPostDomainModel.setId(shortBlogPostSearchResultDto.getId());
        shortBlogPostDomainModel.setTitle(shortBlogPostSearchResultDto.getTitle());
        shortBlogPostDomainModel.setTagList(shortBlogPostSearchResultDto.getTagList());
        shortBlogPostDomainModel.setSummary(shortBlogPostSearchResultDto.getSummary());
        shortBlogPostDomainModel.setAuthorId(shortBlogPostSearchResultDto.getAuthorName());
        shortBlogPostDomainModel.setCoverImageUrl(shortBlogPostSearchResultDto.getImageUrl());
        shortBlogPostDomainModel.setCreatedDateTime(LocalDateTime.parse(shortBlogPostSearchResultDto.getPostDate()));
        return shortBlogPostDomainModel;
    }
}
