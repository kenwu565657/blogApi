package com.contentfarm.outputport.blogpost.search;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;

public interface IBlogPostIndexService {
    void indexBlogPost(BlogPostDomainModel blogPostDomainModel);
    void deleteBlogPostIndex(String id);
}
