package com.contentfarm.persistence.blogpost.mapper;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;
import org.springframework.stereotype.Component;

@Component
public class ShortBlogPostEntityMapper {
    public ShortBlogPostDomainModel mapToShortBlogPostDomainModel(ShortBlogPostEntity shortBlogPostEntity) {
        if (null == shortBlogPostEntity) {
            return null;
        }
        return ShortBlogPostDomainModel
                .builder()
                .id(shortBlogPostEntity.getId())
                .title(shortBlogPostEntity.getTitle())
                .authorId(shortBlogPostEntity.getAuthorId())
                .contentType(shortBlogPostEntity.getContentType())
                .contentFileName(shortBlogPostEntity.getContentFileName())
                .createdDateTime(shortBlogPostEntity.getCreatedDateTime())
                .build();
    }

    public ShortBlogPostEntity mapToBlogPostEntity(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        if (null == shortBlogPostDomainModel) {
            return null;
        }
        return ShortBlogPostEntity
                .builder()
                .id(shortBlogPostDomainModel.getId())
                .title(shortBlogPostDomainModel.getTitle())
                .authorId(shortBlogPostDomainModel.getAuthorId())
                .contentType(shortBlogPostDomainModel.getContentType())
                .contentFileName(shortBlogPostDomainModel.getContentFileName())
                .createdDateTime(shortBlogPostDomainModel.getCreatedDateTime())
                .build();
    }
}
