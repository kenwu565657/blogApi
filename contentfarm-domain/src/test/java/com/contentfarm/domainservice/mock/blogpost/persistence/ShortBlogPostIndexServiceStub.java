package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.ContentFarmIdUtils;
import com.contentfarm.ContentFarmStringUtils;
import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostIndexService;
import com.contentfarm.response.ShortBlogPostSearchResultDto;

import java.util.HashMap;

public class ShortBlogPostIndexServiceStub implements IShortBlogPostIndexService {
    @Override
    public void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        if (ContentFarmStringUtils.isBlank(shortBlogPostDomainModel.getId())) {
            String id = ContentFarmIdUtils.ofRandom();
            shortBlogPostDomainModel.setId(id);
        }
        var shortBlogPostSearchResultDto = ShortBlogPostDomainModelMapper.toShortBlogPostSearchResultDto(shortBlogPostDomainModel);
        getMockElasticSearch().put(shortBlogPostSearchResultDto.getId(), shortBlogPostSearchResultDto);
    }

    @Override
    public void deleteShortBlogPostIndex(String id) {
        getMockElasticSearch().remove(id);
    }

    private HashMap<String, ShortBlogPostSearchResultDto> getMockElasticSearch() {
        return FakeShortBlogPostElasticSearch.INSTANCE.getShortBlogPostSearchResultDtoHashMap();
    }
}
