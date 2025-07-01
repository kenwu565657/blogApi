package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.ContentFarmIdUtils;
import com.contentfarm.ContentFarmStringUtils;
import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceCommandService;

import java.util.HashMap;

/**
 * This class is used as a mock for unit testing
 *
 */
public class ShortBlogPostPersistenceCommandServiceStub implements IShortBlogPostPersistenceCommandService {
    @Override
    public ShortBlogPostDomainModel upsertShortBlogPost(ShortBlogPostDomainModel shortBlogPostDomainModel) {
        if (ContentFarmStringUtils.isBlank(shortBlogPostDomainModel.getId())) {
            String newId = ContentFarmIdUtils.ofRandom();
            shortBlogPostDomainModel.setId(newId);
            getMockDatabase().put(newId, shortBlogPostDomainModel);
        } else {
            getMockDatabase().put(shortBlogPostDomainModel.getId(), shortBlogPostDomainModel);
        }
        return shortBlogPostDomainModel;
    }

    @Override
    public String deleteShortBlogPostById(String id) {
        getMockDatabase().remove(id);
        return id;
    }

    private HashMap<String, ShortBlogPostDomainModel> getMockDatabase() {
        return FakeShortBlogPostDatabase.INSTANCE.getMockDatabase();
    }
}
