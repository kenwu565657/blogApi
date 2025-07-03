package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.persistence.blogpost.mapper.ShortBlogPostEntityMapper;
import com.contentfarm.persistence.blogpost.mock.ShortBlogPostDaoStub;
import com.contentfarm.persistence.blogpost.mock.TestingShortBlogPostObjectGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShortBlogPostPersistenceCommandServiceTest {

    @Test
    void upsertShortBlogPost() {
        ShortBlogPostDaoStub shortBlogPostDaoStub = new ShortBlogPostDaoStub();

        ShortBlogPostPersistenceCommandService shortBlogPostPersistenceCommandService = new ShortBlogPostPersistenceCommandService(
                shortBlogPostDaoStub,
                new ShortBlogPostEntityMapper()
        );

        var testingShortBlogPostDomainModel = TestingShortBlogPostObjectGenerator.createShortBlogPostDomainModel();
        var savedShortBlogPostEntity = shortBlogPostPersistenceCommandService.upsertShortBlogPost(testingShortBlogPostDomainModel);

        var result = shortBlogPostDaoStub.getReferenceById(savedShortBlogPostEntity.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedShortBlogPostEntity.getId(), result.getId());
        final String newAuthorId = "new AuthorId";
        Assertions.assertNotEquals(newAuthorId, result.getAuthorId());
        Assertions.assertNull(result.getLastUpdatedDateTime());

        testingShortBlogPostDomainModel.setAuthorId("new AuthorId");
        shortBlogPostPersistenceCommandService.upsertShortBlogPost(testingShortBlogPostDomainModel);

        var newResult = shortBlogPostDaoStub.getReferenceById(savedShortBlogPostEntity.getId());
        Assertions.assertNotNull(newResult);
        Assertions.assertEquals(savedShortBlogPostEntity.getId(), newResult.getId());
        Assertions.assertEquals(newAuthorId, newResult.getAuthorId());
        Assertions.assertNotNull(newResult.getLastUpdatedDateTime());
    }

    @Test
    void deleteShortBlogPostById() {
        ShortBlogPostDaoStub shortBlogPostDaoStub = new ShortBlogPostDaoStub();

        ShortBlogPostPersistenceCommandService shortBlogPostPersistenceCommandService = new ShortBlogPostPersistenceCommandService(
                shortBlogPostDaoStub,
                new ShortBlogPostEntityMapper()
        );

        var testingShortBlogPostDomainModel = TestingShortBlogPostObjectGenerator.createShortBlogPostDomainModel();
        var savedShortBlogPostEntity = shortBlogPostPersistenceCommandService.upsertShortBlogPost(testingShortBlogPostDomainModel);

        var result = shortBlogPostDaoStub.getReferenceById(savedShortBlogPostEntity.getId());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedShortBlogPostEntity.getId(), result.getId());

        var deletedId = shortBlogPostPersistenceCommandService.deleteShortBlogPostById(savedShortBlogPostEntity.getId());

        var newResult = shortBlogPostDaoStub.getReferenceById(deletedId);
        Assertions.assertNull(newResult);
    }
}