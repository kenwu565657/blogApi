package com.contentfarm.domainservice.blogpost;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.inputport.blogpost.web.IAdminShortBlogPostCommandService;
import com.contentfarm.inputport.blogpost.web.IShortBlogPostWebDomainService;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceCommandService;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceQueryService;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostIndexService;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostSearchService;
import com.contentfarm.response.SearchResult;
import com.contentfarm.response.ShortBlogPostSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortBlogPostDomainService implements IShortBlogPostWebDomainService, IAdminShortBlogPostCommandService {
    private final IShortBlogPostPersistenceCommandService shortBlogPostPersistenceCommandService;
    private final IShortBlogPostPersistenceQueryService shortBlogPostPersistenceQueryService;
    private final IShortBlogPostIndexService shortBlogPostIndexService;
    private final IShortBlogPostSearchService shortBlogPostSearchService;

    @Override
    public void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {

    }

    @Override
    public void deleteShortBlogPost(String shortBlogPostId) {

    }

    @Override
    public ShortBlogPostDomainModel getShortBlogPostById(String id) {
        return null;
    }

    @Override
    public byte[] getBlogPostContentAsMarkdownById(String Id) {
        return new byte[0];
    }

    @Override
    public ShortBlogPostSearchResultDto searchShortBlogPostById(String id) {
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

    @Override
    public SearchResult<ShortBlogPostSearchResultDto> searchAllShortBlogPost() {
        return null;
    }
}
