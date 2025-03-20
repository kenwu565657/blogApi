package com.contentfarm.outputport.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;

import java.util.List;

public interface IBlogPostPersistenceCommandService {
    BlogPostDomainModel upsertBlogPost(BlogPostDomainModel blogPostDomainModel);
    String deleteBlogPostById(String id);
    void addBlogPostTag(List<String> blogPostTagNameList);
    void deleteBlogPostTag(List<String> blogPostTagNameList);
}
