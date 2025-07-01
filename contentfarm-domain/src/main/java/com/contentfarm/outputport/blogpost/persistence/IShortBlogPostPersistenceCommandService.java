package com.contentfarm.outputport.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;

public interface IShortBlogPostPersistenceCommandService {
    ShortBlogPostDomainModel upsertShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel);
    String deleteShortBlogPostById(String id);
}
