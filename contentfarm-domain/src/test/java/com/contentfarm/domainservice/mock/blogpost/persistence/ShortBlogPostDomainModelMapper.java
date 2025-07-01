package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.response.ShortBlogPostSearchResultDto;

public class ShortBlogPostDomainModelMapper {
    private ShortBlogPostDomainModelMapper() {}

    public static ShortBlogPostSearchResultDto toShortBlogPostSearchResultDto(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        if (null == shortBlogPostDomainModel) {
            return null;
        }
        ShortBlogPostSearchResultDto resultDto = new ShortBlogPostSearchResultDto();
        resultDto.setId(shortBlogPostDomainModel.getId());
        resultDto.setTitle(shortBlogPostDomainModel.getTitle());
        resultDto.setTagList(shortBlogPostDomainModel.getTagList());
        resultDto.setAuthorName(shortBlogPostDomainModel.getAuthorId());

        return resultDto;
    }
}
