package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.persistence.blogpost.mapper.ShortBlogPostEntityMapper;
import com.contentfarm.persistence.blogpost.mock.ShortBlogPostDaoStub;
import com.contentfarm.persistence.blogpost.mock.TestingShortBlogPostObjectGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShortBlogPostPersistenceQueryServiceTest {

    @Test
    void getById() {
        ShortBlogPostDaoStub shortBlogPostDaoStub = new ShortBlogPostDaoStub();

        ShortBlogPostPersistenceQueryService shortBlogPostPersistenceQueryService = new ShortBlogPostPersistenceQueryService(
                shortBlogPostDaoStub,
                new ShortBlogPostEntityMapper()
        );

        var testingShortBlogPostEntity = TestingShortBlogPostObjectGenerator.createShortBlogPostEntity();
        shortBlogPostDaoStub.save(testingShortBlogPostEntity);

        var result = shortBlogPostPersistenceQueryService.getById(testingShortBlogPostEntity.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(testingShortBlogPostEntity.getId(), result.getId());
    }
}