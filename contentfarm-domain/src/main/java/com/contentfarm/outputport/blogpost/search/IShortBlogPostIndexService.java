package com.contentfarm.outputport.blogpost.search;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;

public interface IShortBlogPostIndexService {
    void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel);
    void deleteShortBlogPostIndex(String id);
}
