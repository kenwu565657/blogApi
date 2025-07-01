package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.aggregateroot.blogpost.ShortBlogPostDomainModel;
import com.contentfarm.outputport.blogpost.persistence.IShortBlogPostPersistenceQueryService;

import java.util.HashMap;

public class ShortBlogPostPersistenceQueryServiceStub implements IShortBlogPostPersistenceQueryService {
    @Override
    public ShortBlogPostDomainModel getById(String id) {
        return getMockDatabase().get(id);
    }

    private HashMap<String, ShortBlogPostDomainModel> getMockDatabase() {
        return FakeShortBlogPostDatabase.INSTANCE.getMockDatabase();
    }
}
