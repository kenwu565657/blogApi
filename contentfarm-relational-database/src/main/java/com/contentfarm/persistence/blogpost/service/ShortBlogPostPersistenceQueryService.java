package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceQueryService;
import com.contentfarm.persistence.blogpost.dao.IShortBlogPostDao;
import com.contentfarm.persistence.blogpost.mapper.ShortBlogPostEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortBlogPostPersistenceQueryService implements IShortBlogPostPersistenceQueryService {
    private final IShortBlogPostDao shortBlogPostDao;
    private final ShortBlogPostEntityMapper shortBlogPostEntityMapper;

    @Override
    public ShortBlogPostDomainModel getById(String id) {
        var shortBlogPostEntity = shortBlogPostDao.getReferenceById(id);
        return shortBlogPostEntityMapper.mapToShortBlogPostDomainModel(shortBlogPostEntity);
    }
}
