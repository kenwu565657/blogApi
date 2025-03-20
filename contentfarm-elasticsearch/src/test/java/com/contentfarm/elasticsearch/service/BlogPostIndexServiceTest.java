package com.contentfarm.elasticsearch.service;

import com.contentfarm.elasticsearch.TestContainerElasticsearch;
import com.contentfarm.elasticsearch.document.BlogPostDocument;
import com.contentfarm.elasticsearch.exception.DocumentIndexException;
import com.contentfarm.elasticsearch.mapper.BlogPostSearchMapper;
import com.contentfarm.elasticsearch.repository.IBlogPostElasticsearchRepository;
import com.contentfarm.outputport.blogpost.search.IBlogPostIndexService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

import java.text.MessageFormat;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(TestContainerElasticsearch.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestConfiguration.class)
class BlogPostIndexServiceTest {

    @Autowired
    IBlogPostIndexService blogPostIndexService;

    @Autowired
    BlogPostSearchMapper blogPostSearchMapper;

    @Autowired
    IBlogPostElasticsearchRepository blogPostElasticsearchRepository;

    @BeforeAll
    void setUp() {
        blogPostElasticsearchRepository.deleteAll();
    }

    @Test
    @Order(1)
    void addDocument() {
        var document = buildNormalTestBlogPostDocument(0);
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        Assertions.assertDoesNotThrow(() -> blogPostIndexService.indexBlogPost(domainModel));
        var isExist = blogPostElasticsearchRepository.existsById("testingId0");
        Assertions.assertTrue(isExist);
        var count = blogPostElasticsearchRepository.count();
        Assertions.assertEquals(1, count);
        var result = blogPostElasticsearchRepository.findById("testingId0");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testingId0", result.get().getId());
        Assertions.assertEquals("testingTitle0", result.get().getTitle());
        Assertions.assertEquals(List.of("testingTag1", "testingTag2"), result.get().getTagList());
    }

    @Test
    @Order(2)
    void addDocument_sameIdReIndex() {
        var document = buildNormalTestBlogPostDocument(0);
        document.setTitle("newTestingTitle1");
        document.setTagList(List.of("newTestingTag1", "newTestingTag2"));
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        Assertions.assertDoesNotThrow(() -> blogPostIndexService.indexBlogPost(domainModel));
        var isExist = blogPostElasticsearchRepository.existsById("testingId0");
        Assertions.assertTrue(isExist);
        var count = blogPostElasticsearchRepository.count();
        Assertions.assertEquals(1, count);
        var result = blogPostElasticsearchRepository.findById("testingId0");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testingId0", result.get().getId());
        Assertions.assertEquals("newTestingTitle1", result.get().getTitle());
        Assertions.assertEquals(List.of("newTestingTag1", "newTestingTag2"), result.get().getTagList());
    }

    @Test
    @Order(3)
    void addDocument_differentId() {
        var document = buildNormalTestBlogPostDocument(1);
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        Assertions.assertDoesNotThrow(() -> blogPostIndexService.indexBlogPost(domainModel));
        var isExist = blogPostElasticsearchRepository.existsById("testingId1");
        Assertions.assertTrue(isExist);
        var count = blogPostElasticsearchRepository.count();
        Assertions.assertEquals(2, count);
    }

    @Test
    @Order(4)
    void addDocument_nullTagList() {
        var document = buildNormalTestBlogPostDocument(2);
        document.setTagList(null);
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        Assertions.assertDoesNotThrow(() -> blogPostIndexService.indexBlogPost(domainModel));
        var isExist = blogPostElasticsearchRepository.existsById("testingId2");
        Assertions.assertTrue(isExist);
        var count = blogPostElasticsearchRepository.count();
        Assertions.assertEquals(3, count);
        var result = blogPostElasticsearchRepository.findById("testingId2");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testingId2", result.get().getId());
        Assertions.assertTrue(result.get().getTagList().isEmpty());
    }

    @Test
    void addDocument_nullCase() {
        DocumentIndexException documentIndexException = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(null));
        Assertions.assertNotNull(documentIndexException);
        Assertions.assertEquals("Can Not Index Null Document.", documentIndexException.getMessage());
    }

    @Test
    void addDocument_emptyBean() {
        var document = BlogPostDocument.builder().build();
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        DocumentIndexException documentIndexException = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(domainModel));
        Assertions.assertNotNull(documentIndexException);
        Assertions.assertEquals("Found Missing field(s): Id, Title, Summary, Author Name, Post Date, Image Url.", documentIndexException.getMessage());
    }

    @Test
    void addDocument_blankId() {
        var document = buildNormalTestBlogPostDocument(0);
        document.setId(" ");
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        DocumentIndexException documentIndexException = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(domainModel));
        Assertions.assertNotNull(documentIndexException);
        Assertions.assertEquals("Found Missing field(s): Id.", documentIndexException.getMessage());

        var document2 = buildNormalTestBlogPostDocument(0);
        document2.setId("");
        var domainModel2 = blogPostSearchMapper.toBlogPostDomainModel(document2);
        DocumentIndexException documentIndexException2 = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(domainModel2));
        Assertions.assertNotNull(documentIndexException2);
        Assertions.assertEquals("Found Missing field(s): Id.", documentIndexException2.getMessage());
    }

    @Test
    void addDocument_blankFields() {
        var document = buildNormalTestBlogPostDocument(0);
        document.setId("");
        document.setTitle("");
        document.setSummary("");
        document.setAuthorName("");
        document.setImageUrl("");
        document.setPostDate("");
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        DocumentIndexException documentIndexException = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(domainModel));
        Assertions.assertNotNull(documentIndexException);
        Assertions.assertEquals("Found Missing field(s): Id, Title, Summary, Author Name, Post Date, Image Url.", documentIndexException.getMessage());
    }

    @Test
    void addDocument_spaceFields() {
        var document = buildNormalTestBlogPostDocument(0);
        document.setId("  ");
        document.setTitle(" ");
        document.setSummary(" ");
        document.setAuthorName("   ");
        document.setImageUrl(" ");
        document.setPostDate(" ");
        var domainModel = blogPostSearchMapper.toBlogPostDomainModel(document);
        DocumentIndexException documentIndexException = Assertions.assertThrows(DocumentIndexException.class, () -> blogPostIndexService.indexBlogPost(domainModel));
        Assertions.assertNotNull(documentIndexException);
        Assertions.assertEquals("Found Missing field(s): Id, Title, Summary, Author Name, Post Date, Image Url.", documentIndexException.getMessage());
    }

    private BlogPostDocument buildNormalTestBlogPostDocument(int orderNumber) {
        return BlogPostDocument
                .builder()
                .id(MessageFormat.format("testingId{0}", orderNumber))
                .title(MessageFormat.format("testingTitle{0}", orderNumber))
                .tagList(List.of("testingTag1", "testingTag2"))
                .summary(MessageFormat.format("This is a testingSummary{0} keyword", orderNumber))
                .authorName(MessageFormat.format("testingAuthor{0}", orderNumber))
                .imageUrl(MessageFormat.format("testingImage{0}", orderNumber))
                .postDate("2025-03-20")
                .build();
    }
}