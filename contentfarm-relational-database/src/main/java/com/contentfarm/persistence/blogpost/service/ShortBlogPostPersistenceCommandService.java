package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.ContentFarmIdUtils;
import com.contentfarm.ContentFarmLocaleDateTimeUtils;
import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceCommandService;
import com.contentfarm.persistence.blogpost.dao.IShortBlogPostDao;
import com.contentfarm.persistence.blogpost.mapper.ShortBlogPostEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortBlogPostPersistenceCommandService implements IShortBlogPostPersistenceCommandService {
    private final IShortBlogPostDao shortBlogPostDao;
    private final ShortBlogPostEntityMapper shortBlogPostEntityMapper;

    @Override
    public ShortBlogPostDomainModel upsertShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        var shortBlogPostEntity = shortBlogPostEntityMapper.mapToBlogPostEntity(shortBlogPostDomainModel);
        if (null == shortBlogPostEntity.getId()) {
            String uuid = ContentFarmIdUtils.ofRandom();
            shortBlogPostEntity.setId(uuid);
            shortBlogPostDomainModel.setId(uuid);
        } else {
            shortBlogPostEntity.setLastUpdatedDateTime(ContentFarmLocaleDateTimeUtils.ofNow());
        }
        shortBlogPostDao.save(shortBlogPostEntity);
        return shortBlogPostDomainModel;
    }

    @Override
    public String deleteShortBlogPostById(String id) {
        shortBlogPostDao.deleteById(id);
        return id;
    }
}
