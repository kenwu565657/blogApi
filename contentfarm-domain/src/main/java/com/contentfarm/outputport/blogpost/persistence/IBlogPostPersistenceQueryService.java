package com.contentfarm.outputport.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;

import java.util.List;

public interface IBlogPostPersistenceQueryService {
    BlogPostDomainModel getById(String id);
    List<String> findTagList();
}
