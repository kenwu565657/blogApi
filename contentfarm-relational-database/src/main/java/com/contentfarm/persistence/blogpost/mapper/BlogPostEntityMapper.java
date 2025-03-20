package com.contentfarm.persistence.blogpost.mapper;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.persistence.blogpost.entity.BlogPostEntity;
import org.springframework.stereotype.Component;

@Component
public class BlogPostEntityMapper {
    public BlogPostDomainModel mapToBlogPostDomainModel(BlogPostEntity blogPostEntity) {
        if (null == blogPostEntity) {
            return null;
        }
        return BlogPostDomainModel
                .builder()
                .id(blogPostEntity.getId())
                .title(blogPostEntity.getTitle())
                .authorId(blogPostEntity.getAuthorId())
                .contentType(blogPostEntity.getContentType())
                .contentFileName(blogPostEntity.getContentFileName())
                .createdDateTime(blogPostEntity.getCreatedDateTime())
                .build();
    }

    public BlogPostEntity mapToBlogPostEntity(BlogPostDomainModel blogPostDomainModel) {
        if (null == blogPostDomainModel) {
            return null;
        }
        return BlogPostEntity
                .builder()
                .id(blogPostDomainModel.getId())
                .title(blogPostDomainModel.getTitle())
                .authorId(blogPostDomainModel.getAuthorId())
                .contentType(blogPostDomainModel.getContentType())
                .contentFileName(blogPostDomainModel.getContentFileName())
                .createdDateTime(blogPostDomainModel.getCreatedDateTime())
                .build();
    }
}
