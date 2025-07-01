package com.contentfarm.inputport.blogpost.web;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;

public interface IAdminShortBlogPostCommandService {
    void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel);
    void deleteShortBlogPost(String shortBlogPostId);
}
