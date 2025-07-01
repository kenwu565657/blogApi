package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.response.ShortBlogPostSearchResultDto;
import lombok.Getter;

import java.util.HashMap;

@Getter
public enum FakeShortBlogPostElasticSearch {
    INSTANCE;
    private final HashMap<String, ShortBlogPostSearchResultDto> shortBlogPostSearchResultDtoHashMap = new HashMap<>();
}
