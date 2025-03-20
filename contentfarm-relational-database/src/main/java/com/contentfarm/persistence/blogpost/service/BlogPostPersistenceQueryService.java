package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.persistence.blogpost.dao.IBlogPostDao;
import com.contentfarm.persistence.blogpost.dao.IBlogPostTagDao;
import com.contentfarm.persistence.blogpost.entity.BlogPostTagEntity;
import com.contentfarm.persistence.blogpost.mapper.BlogPostEntityMapper;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostPersistenceQueryService implements IBlogPostPersistenceQueryService {
    private final IBlogPostDao blogPostDao;
    private final IBlogPostTagDao blogPostTagDao;
    private final BlogPostEntityMapper blogPostEntityMapper;

    @Override
    public BlogPostDomainModel getById(String id) {
        var blogPostEntity = blogPostDao.getReferenceById(id);
        return blogPostEntityMapper.mapToBlogPostDomainModel(blogPostEntity);
    }

    @Override
    public List<String> findTagList() {
        return blogPostTagDao.findAll().stream().map(BlogPostTagEntity::getTagName).toList();
    }
}
