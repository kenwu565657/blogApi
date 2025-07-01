package com.contentfarm.outputport.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;

public interface IShortBlogPostPersistenceQueryService {
    ShortBlogPostDomainModel getById(String id);
}
