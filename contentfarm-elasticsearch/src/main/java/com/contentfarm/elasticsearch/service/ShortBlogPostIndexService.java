package com.contentfarm.elasticsearch.service;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.elasticsearch.repository.IShortBlogPostElasticsearchRepository;
import com.contentfarm.outputport.blogpost.search.IShortBlogPostIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortBlogPostIndexService implements IShortBlogPostIndexService {
    private final IShortBlogPostElasticsearchRepository shortBlogPostElasticsearchRepository;

    @Override
    public void indexShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {

    }

    @Override
    public void deleteShortBlogPostIndex(String id) {

    }
}
