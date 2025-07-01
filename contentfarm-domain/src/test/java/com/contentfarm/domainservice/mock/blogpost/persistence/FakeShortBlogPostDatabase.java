package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import lombok.Getter;

import java.util.HashMap;

@Getter
public enum FakeShortBlogPostDatabase {
    INSTANCE;
    private final HashMap<String, ShortBlogPostDomainModel> mockDatabase = new HashMap<>();
}
