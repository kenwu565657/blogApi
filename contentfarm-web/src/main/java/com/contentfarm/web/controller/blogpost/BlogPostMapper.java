package com.contentfarm.web.controller.blogpost;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BlogPostMapper {

    public BlogPostSummaryDto toBlogPostSummaryDto(BlogPostDomainModel blogPostDomainModel) {
        if (null == blogPostDomainModel) {
            return null;
        }
        var blogPostSummaryDto = new BlogPostSummaryDto();
        blogPostSummaryDto.setId(blogPostDomainModel.getId());
        blogPostSummaryDto.setTitle(blogPostDomainModel.getTitle());
        blogPostSummaryDto.setSummary(blogPostDomainModel.getSummary());
        blogPostSummaryDto.setTagList(Optional.ofNullable(blogPostDomainModel.getTagList()).orElse(List.of()));
        blogPostSummaryDto.setAuthorId(blogPostDomainModel.getAuthorId());
        blogPostSummaryDto.setCreatedDateTime(blogPostDomainModel.getCreatedDateTime());
        return blogPostSummaryDto;
    }

}
