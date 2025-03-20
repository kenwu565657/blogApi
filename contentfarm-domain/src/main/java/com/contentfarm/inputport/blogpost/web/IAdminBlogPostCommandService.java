package com.contentfarm.inputport.blogpost.web;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.command.AdminAddBlogPostTagCommand;
import com.contentfarm.command.AdminDeleteBlogPostCommand;
import com.contentfarm.command.AdminDeleteBlogPostTagCommand;
import com.contentfarm.command.AdminIndexBlogPostCommand;

import java.util.List;

public interface IAdminBlogPostCommandService {
    void indexBlogPost(BlogPostDomainModel blogPostDomainModel);
    void deleteBlogPost(String blogPostId);
    void addBlogPostTag(List<String> blogPostTagNameList);
    void deleteBlogPostTag(List<String> blogPostTagNameList);
}
