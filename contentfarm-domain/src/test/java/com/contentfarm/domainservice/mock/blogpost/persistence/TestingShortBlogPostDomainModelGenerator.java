package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;

import java.util.List;

public class TestingShortBlogPostDomainModelGenerator {

    public static ShortBlogPostDomainModel generateShortBlogPostDomainModel() {
        ShortBlogPostDomainModel shortBlogPostDomainModel = new ShortBlogPostDomainModel();
        shortBlogPostDomainModel.setId(TESTING_SHORT_BLOG_POST_ID);
        shortBlogPostDomainModel.setTitle(TESTING_SHORT_BLOG_POST_TITLE);
        shortBlogPostDomainModel.setAuthorId(TESTING_SHORT_BLOG_POST_AUTHOR_ID);
        shortBlogPostDomainModel.setTagList(List.of(TESTING_SHORT_BLOG_POST_TAG_1, TESTING_SHORT_BLOG_POST_TAG_2));

        return shortBlogPostDomainModel;
    }

    public static final String TESTING_SHORT_BLOG_POST_ID = "testing-short-blog-post-id";
    public static final String TESTING_SHORT_BLOG_POST_SEARCH_KEYWORD = "TKeyword";
    public static final String TESTING_SHORT_BLOG_POST_TITLE = "Testing Short Blog Post Title " + TESTING_SHORT_BLOG_POST_SEARCH_KEYWORD;
    public static final String TESTING_SHORT_BLOG_POST_AUTHOR_ID = "testing-short-blog-post-author-id";
    public static final String TESTING_SHORT_BLOG_POST_TAG_1 = "Testing Short Blog Post Tag";
    public static final String TESTING_SHORT_BLOG_POST_TAG_2 = "Testing Short Blog Post Tag 2";
}
